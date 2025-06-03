import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;

public class AuthenticationHelper {

    /**
     * Melakukan registrasi pengguna baru.
     * @return Email yang digunakan untuk registrasi jika berhasil, null jika gagal.
     */
    public static String registerNewUser(AppiumUtils utils, String fullName, String email, String password) throws InterruptedException {
        System.out.println("--- Proses Registrasi Pengguna Baru ---");
        utils.click(By.id("com.example.fraga:id/textViewSignUp"));
        
        utils.sendKeys(By.id("com.example.fraga:id/editTextFullName"), fullName);
        utils.sendKeys(By.id("com.example.fraga:id/editTextEmail"), email);
        utils.sendKeys(By.id("com.example.fraga:id/editTextPassword"), password);
        utils.sendKeys(By.id("com.example.fraga:id/editTextConfirmPassword"), password);
        
        utils.click(By.id("com.example.fraga:id/buttonRegister"));
        System.out.println("Tombol Register diklik.");

        try {
            utils.waitForElementToBeVisible(By.id("com.example.fraga:id/buttonLogin")); // Verifikasi kembali ke halaman login
            System.out.println("Registrasi berhasil untuk email: " + email);
            return email;
        } catch (Exception e) {
            System.out.println("Registrasi GAGAL atau tidak kembali ke halaman Login.");
            return null;
        }
    }

    /**
     * Melakukan login pengguna.
     * @return true jika login berhasil (sampai ke halaman Track), false jika gagal.
     */
    public static boolean login(AppiumUtils utils, String email, String password) {
        System.out.println("--- Proses Login Pengguna: " + email + " ---");
        try {
             utils.waitForElementToBeVisible(By.id("com.example.fraga:id/editTextEmail"));
        } catch (Exception e) {
            System.out.println("Tidak berada di halaman login atau elemen email tidak ditemukan.");
            return false;
        }

        utils.sendKeys(By.id("com.example.fraga:id/editTextEmail"), email);
        utils.sendKeys(By.id("com.example.fraga:id/editTextPassword"), password);
        utils.click(By.id("com.example.fraga:id/buttonLogin"));
        System.out.println("Tombol Login diklik.");

        try {
            utils.waitForElementToBeVisible(By.id("com.example.fraga:id/buttonStartTracking")); // Verifikasi masuk ke halaman Track
            System.out.println("Login berhasil untuk " + email + ", berada di halaman utama (Track).");
            return true;
        } catch (Exception e) {
            System.out.println("Login GAGAL untuk " + email + " atau tidak masuk ke halaman utama.");
            return false;
        }
    }

    /**
     * Melakukan logout dari halaman Settings.
     * Termasuk menangani dialog konfirmasi logout.
     * @return true jika logout berhasil (kembali ke halaman Login), false jika gagal.
     */
    public static boolean logoutFromSettings(AppiumUtils utils) {
        System.out.println("--- Proses Logout ---");
        try {
            // Langkah 1: Klik opsi Logout awal di halaman Settings
            utils.scrollToElementByResourceId("com.example.fraga:id/layoutLogout");
            utils.click(By.id("com.example.fraga:id/layoutLogout"));
            System.out.println("Opsi 'Logout' di Settings (dalam layout) diklik.");

            // Langkah 2: TAMBAHAN - Klik tombol LOGOUT pada dialog konfirmasi
            // Tombol LOGOUT di dialog memiliki resource-id="android:id/button1"
            // dan teks "LOGOUT"
            System.out.println("Menunggu dialog konfirmasi logout...");
            utils.waitForElementToBeClickable(By.id("android:id/button1")); // Tunggu tombol di dialog
            utils.click(By.id("android:id/button1")); // Klik tombol LOGOUT di dialog
            System.out.println("Tombol 'LOGOUT' pada dialog konfirmasi diklik.");

            // Langkah 3: Verifikasi kembali ke halaman Login
            utils.waitForElementToBeVisible(By.id("com.example.fraga:id/editTextEmail")); 
            System.out.println("Logout berhasil! Kembali ke Halaman Login.");
            return true;
        } catch (Exception e) {
            System.out.println("Logout GAGAL atau tidak kembali ke halaman Login.");
            e.printStackTrace(); // Cetak error untuk membantu debugging
            return false;
        }
    }
}
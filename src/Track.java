import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;

public class Track extends base {

    public static void performLogout(AndroidDriver<AndroidElement> driver, WebDriverWait wait) {
        System.out.println("\n--- Memulai Proses Logout ---");
        // 1. Navigasi ke halaman 'Profile'
        By profileNavButtonLocator = By.id("com.example.fraga:id/navigation_profile");
        wait.until(ExpectedConditions.elementToBeClickable(profileNavButtonLocator));
        driver.findElement(profileNavButtonLocator).click();
        System.out.println("Navigasi ke halaman 'Profile' untuk logout...");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/textViewProfileTitle")));
        System.out.println("Berada di halaman 'Profile'.");

        // 2. Scroll ke bawah dan klik tombol 'SETTINGS'
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"com.example.fraga:id/buttonSettings\").instance(0))");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("com.example.fraga:id/buttonSettings"))).click();
        System.out.println("Tombol 'SETTINGS' diklik.");

        // 3. Validasi halaman 'Settings'
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Settings']")));
        System.out.println("Berada di halaman 'Settings'.");

        // 4. Scroll ke bawah dan klik tombol 'Logout'
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"com.example.fraga:id/layoutLogout\").instance(0))");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("com.example.fraga:id/layoutLogout"))).click();
        System.out.println("Tombol 'Logout' di Settings diklik.");

        // 5. Validasi dialog konfirmasi Logout
        By confirmLogoutTitleLocator = By.id("com.example.fraga:id/alertTitle"); // ID dari XML dialog
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmLogoutTitleLocator));
        System.out.println("Dialog 'Confirm Logout' muncul.");

        // 6. Klik tombol 'LOGOUT' pada dialog konfirmasi
        wait.until(ExpectedConditions.elementToBeClickable(By.id("android:id/button1"))).click(); // ID dari XML dialog
        System.out.println("Tombol 'LOGOUT' pada dialog diklik.");

        // 7. Validasi akhir: kembali ke halaman Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/editTextEmail")));
        System.out.println("VALIDASI BERHASIL: Logout sukses dan kembali ke halaman Login.");
    }

    public static void main(String[] args) {
        AndroidDriver<AndroidElement> driver = null;
        WebDriverWait wait = null;
        try {
            driver = capabilities();
            System.out.println("Aplikasi Fraga berhasil diluncurkan.");
            wait = new WebDriverWait(driver, 25);

            // --- LANGKAH LOGIN ---
            System.out.println("Mencari field Email...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/editTextEmail")));
            AndroidElement emailField = driver.findElement(By.id("com.example.fraga:id/editTextEmail"));
            emailField.sendKeys("appium@test.com"); 
            System.out.println("Field Email diisi.");
            AndroidElement passwordField = driver.findElement(By.id("com.example.fraga:id/editTextPassword"));
            passwordField.sendKeys("test123");
            System.out.println("Field Password diisi.");

            By loginButtonLocator = By.id("com.example.fraga:id/buttonLogin");
            wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
            driver.findElement(loginButtonLocator).click();
            System.out.println("Tombol LOGIN diklik.");

            // --- LANGKAH START & STOP TRACKING ---
            By trackTitleLocator = By.id("com.example.fraga:id/textViewTrackingTitle");
            wait.until(ExpectedConditions.visibilityOfElementLocated(trackTitleLocator));
            System.out.println("\nVALIDASI BERHASIL: Halaman 'Track' berhasil dimuat setelah login.");

            AndroidElement cyclingRadioButton = driver.findElement(By.id("com.example.fraga:id/radioButtonCycling"));
            cyclingRadioButton.click();
            System.out.println("Memilih aktivitas: Cycling.");

            By trackingButtonLocator = By.id("com.example.fraga:id/buttonStartTracking");
            
            // 8. Klik tombol 'START TRACKING'
            AndroidElement startButton = driver.findElement(trackingButtonLocator);
            startButton.click();
            System.out.println("Tombol 'START TRACKING' diklik.");

            // 9. VALIDASI AKHIR : Verifikasi bahwa tombol berubah menjadi 'STOP TRACKING'
            wait.until(ExpectedConditions.textToBe(trackingButtonLocator, "STOP TRACKING"));
            System.out.println("VALIDASI BERHASIL: Pelacakan dimulai, tombol kini bertuliskan 'STOP TRACKING'.");

            // 10. Klik tombol 'STOP TRACKING'
            driver.findElement(trackingButtonLocator).click();
            System.out.println("Tombol 'STOP TRACKING' diklik.");

            // 11. Validasi bahwa tombol kembali menjadi 'START TRACKING'
            wait.until(ExpectedConditions.textToBe(trackingButtonLocator, "START TRACKING"));
            System.out.println("VALIDASI BERHASIL: Pelacakan dihentikan, tombol kembali ke 'START TRACKING'.");
            System.out.println("=== TEST CASE START & STOP TRACKING SELESAI ===");
            
            // --- LANGKAH LOGOUT ---
            performLogout(driver, wait);

            System.out.println("\n=== SEMUA TEST CASE SELESAI ===");

        } catch (MalformedURLException e) {
            System.err.println("URL server Appium tidak valid: " + e.getMessage());
            e.printStackTrace();
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("\nVALIDASI GAGAL (Timeout): Elemen tidak ditemukan atau kondisi tidak terpenuhi dalam waktu yang ditentukan.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Terjadi kesalahan selama eksekusi tes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("\nSesi driver ditutup.");
            }
        }
    }
}
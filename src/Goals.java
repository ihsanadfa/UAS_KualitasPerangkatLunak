import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;

public class Goals extends base { // Nama kelas sesuai permintaan Anda

    // Fungsi bantuan untuk Logout (diambil dari skrip Anda sebelumnya)
    public static void performLogout(AndroidDriver<AndroidElement> driver, WebDriverWait wait) {
        System.out.println("\n--- Memulai Proses Logout ---");
        // 1. Navigasi ke halaman 'Profile'
        By profileNavButtonLocator = By.id("com.example.fraga:id/navigation_profile");
        // Pastikan elemen navigasi profil ada dan bisa diklik sebelum berpindah
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

            // Validasi sudah masuk halaman setelah login (misal, halaman Track)
            By trackTitleLocator = By.id("com.example.fraga:id/textViewTrackingTitle"); 
            wait.until(ExpectedConditions.visibilityOfElementLocated(trackTitleLocator));
            System.out.println("VALIDASI BERHASIL: Login sukses, berada di halaman 'Track'.");

            // --- LANGKAH TEST HALAMAN GOALS ---
            // 2. Navigasi ke halaman 'Goals'
            By goalsNavButtonLocator = By.id("com.example.fraga:id/navigation_challenges");
            wait.until(ExpectedConditions.elementToBeClickable(goalsNavButtonLocator));
            driver.findElement(goalsNavButtonLocator).click();
            System.out.println("\nMenavigasi ke halaman 'Goals'...");
            
            // 3. VALIDASI: Pastikan sudah berada di halaman 'Goals'
            // XML menunjukkan textViewProfileTitle digunakan juga untuk judul "Goals"
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/textViewProfileTitle")));
            System.out.println("VALIDASI BERHASIL: Berada di halaman 'Goals'.");
            
            // 4. Scroll ke tombol "SYNC STEPS" pada Goal 2 dan klik
            By syncStepsButtonLocator = By.id("com.example.fraga:id/buttonCompleteGoal2");
            System.out.println("Mencari tombol 'SYNC STEPS' untuk Goal 2...");
            // Scroll ke elemen tombol "SYNC STEPS" (buttonCompleteGoal2)
            driver.findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" +
                "new UiSelector().resourceId(\"com.example.fraga:id/buttonCompleteGoal2\").instance(0))");
            
            wait.until(ExpectedConditions.elementToBeClickable(syncStepsButtonLocator));
            driver.findElement(syncStepsButtonLocator).click();
            System.out.println("Tombol 'SYNC STEPS' untuk Goal 2 diklik.");

            // 5. Validasi pop-up "Sync Steps" muncul
            By syncPopupTitleLocator = By.xpath("//android.widget.TextView[@resource-id='com.example.fraga:id/alertTitle' and @text='Sync Steps']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(syncPopupTitleLocator));
            System.out.println("VALIDASI BERHASIL: Pop-up 'Sync Steps' muncul.");
            
            // 6. Klik tombol "OK" pada pop-up
            By okButtonPopupLocator = By.id("android:id/button1"); // Sesuai XML pop-up
            wait.until(ExpectedConditions.elementToBeClickable(okButtonPopupLocator));
            driver.findElement(okButtonPopupLocator).click();
            System.out.println("Tombol 'OK' pada pop-up 'Sync Steps' diklik.");

            // 7. Validasi pop-up "Sync Steps" hilang
            wait.until(ExpectedConditions.invisibilityOfElementLocated(syncPopupTitleLocator));
            System.out.println("VALIDASI BERHASIL: Pop-up 'Sync Steps' telah hilang.");
            System.out.println("=== TEST CASE SYNC STEPS GOAL SELESAI ===");

            // --- LANGKAH LOGOUT ---
            performLogout(driver, wait); // Memanggil fungsi logout

            System.out.println("\n=== SEMUA TEST CASE (SYNC STEPS & LOGOUT) SELESAI ===");

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
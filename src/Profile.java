import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;

public class Profile extends base {

    public static void performLogout(AndroidDriver<AndroidElement> driver, WebDriverWait wait) {
        System.out.println("\n--- Memulai Proses Logout dari Settings (jika belum di sana) ---");
        
        try {
            driver.findElement(By.xpath("//android.widget.TextView[@text='Settings']"));
            System.out.println("Sudah berada di halaman Settings.");
        } catch (NoSuchElementException e) {
            System.out.println("Belum di halaman Settings, navigasi ke Profile > Settings...");
            By profileNavButtonLocator = By.id("com.example.fraga:id/navigation_profile");
            wait.until(ExpectedConditions.elementToBeClickable(profileNavButtonLocator));
            driver.findElement(profileNavButtonLocator).click();
            System.out.println("Navigasi ke halaman 'Profile' untuk logout...");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/textViewProfileTitle")));
            System.out.println("Berada di halaman 'Profile'.");

            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"com.example.fraga:id/buttonSettings\").instance(0))");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("com.example.fraga:id/buttonSettings"))).click();
            System.out.println("Tombol 'SETTINGS' diklik.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Settings']")));
            System.out.println("Berada di halaman 'Settings'.");
        }

        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"com.example.fraga:id/layoutLogout\").instance(0))");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("com.example.fraga:id/layoutLogout"))).click();
        System.out.println("Tombol 'Logout' di Settings diklik.");

        By confirmLogoutTitleLocator = By.id("com.example.fraga:id/alertTitle");
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmLogoutTitleLocator));
        System.out.println("Dialog 'Confirm Logout' muncul.");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("android:id/button1"))).click();
        System.out.println("Tombol 'LOGOUT' pada dialog diklik.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/editTextEmail")));
        System.out.println("VALIDASI BERHASIL: Logout sukses dan kembali ke halaman Login.");
    }

    private static void toggleSwitchIfOn(AndroidDriver<AndroidElement> driver, WebDriverWait wait, By switchLocator, String switchName) {
        System.out.println("Memeriksa status switch: " + switchName);
        try {
             driver.findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" +
                "new UiSelector().resourceIdMatches(\"" + switchLocator.toString().split(": ")[1] + "\").instance(0))");
        } catch (Exception e) {
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(switchLocator));
        AndroidElement notificationSwitch = driver.findElement(switchLocator);
        
        wait.until(ExpectedConditions.visibilityOf(notificationSwitch));

        if ("true".equals(notificationSwitch.getAttribute("checked"))) {
            System.out.println(switchName + " aktif, akan dimatikan.");
            notificationSwitch.click();
            wait.until(ExpectedConditions.attributeToBe(switchLocator, "checked", "false"));
            System.out.println(switchName + " berhasil dimatikan.");
        } else {
            System.out.println(switchName + " sudah mati.");
        }
    }

    public static void main(String[] args) {
        AndroidDriver<AndroidElement> driver = null;
        WebDriverWait wait = null;
        try {
            driver = capabilities();
            System.out.println("Aplikasi Fraga berhasil diluncurkan.");
            wait = new WebDriverWait(driver, 25);

            // --- LANGKAH LOGIN  ---
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

            By trackTitleLocator = By.id("com.example.fraga:id/textViewTrackingTitle"); 
            wait.until(ExpectedConditions.visibilityOfElementLocated(trackTitleLocator));
            System.out.println("VALIDASI BERHASIL: Login sukses, berada di halaman 'Track'.");

            // --- LANGKAH TEST PROFILE & SETTINGS ---
            // 1. Navigasi ke halaman 'Profile'
            By profileNavButtonLocator = By.id("com.example.fraga:id/navigation_profile");
            wait.until(ExpectedConditions.elementToBeClickable(profileNavButtonLocator));
            driver.findElement(profileNavButtonLocator).click();
            System.out.println("\nMenavigasi ke halaman 'Profile'...");
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/textViewProfileTitle")));
            System.out.println("VALIDASI BERHASIL: Berada di halaman 'Profile'.");

            // 2. Scroll ke bawah dan klik tombol 'SETTINGS'
            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"com.example.fraga:id/buttonSettings\").instance(0))");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("com.example.fraga:id/buttonSettings"))).click();
            System.out.println("Tombol 'SETTINGS' diklik.");

            // 3. VALIDASI: Pastikan sudah masuk ke halaman 'Settings'
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Settings']")));
            System.out.println("VALIDASI BERHASIL: Berada di halaman 'Settings'.");

            // 4. Mematikan semua switch notifikasi
            // Scroll untuk memastikan semua switch terlihat sebelum diinteraksi
            // Bagian "Notifications" mungkin perlu di-scroll agar semua switch terlihat
            System.out.println("\nMemproses Switch Notifikasi...");
            
            // Menggunakan fungsi bantuan untuk toggle switch
            toggleSwitchIfOn(driver, wait, By.id("com.example.fraga:id/switchActivityReminders"), "Activity Reminders");
            toggleSwitchIfOn(driver, wait, By.id("com.example.fraga:id/switchKudosNotifications"), "Kudos Notifications");
            toggleSwitchIfOn(driver, wait, By.id("com.example.fraga:id/switchFriendActivities"), "Friend Activities");
            
            System.out.println("Semua switch notifikasi telah diproses.");

            // --- LANGKAH LOGOUT ---
            performLogout(driver, wait);

            System.out.println("\n=== SEMUA TEST CASE (TOGGLE NOTIFICATIONS & LOGOUT) SELESAI ===");

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
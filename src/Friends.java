import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException; // Import untuk menangani jika elemen tidak ada
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;

public class Friends extends base {

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
        driver.findElement(By.id("com.example.fraga:id/buttonSettings")).click();
        System.out.println("Tombol 'SETTINGS' diklik.");

        // 3. Validasi halaman 'Settings'
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Settings']")));
        System.out.println("Berada di halaman 'Settings'.");

        // 4. Scroll ke bawah dan klik tombol 'Logout'
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"com.example.fraga:id/layoutLogout\").instance(0))");
        driver.findElement(By.id("com.example.fraga:id/layoutLogout")).click();
        System.out.println("Tombol 'Logout' di Settings diklik.");

        // 5. Validasi dialog konfirmasi Logout
        By confirmLogoutTitleLocator = By.id("com.example.fraga:id/alertTitle");
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmLogoutTitleLocator));
        System.out.println("Dialog 'Confirm Logout' muncul.");

        // 6. Klik tombol 'LOGOUT' pada dialog konfirmasi
        driver.findElement(By.id("android:id/button1")).click(); 
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

            By trackTitleLocator = By.id("com.example.fraga:id/textViewTrackingTitle"); 
            wait.until(ExpectedConditions.visibilityOfElementLocated(trackTitleLocator));
            System.out.println("VALIDASI BERHASIL: Login sukses, berada di halaman 'Track'.");

            // --- LANGKAH TEST HALAMAN FRIENDS ---
            // 2. Navigasi ke halaman 'Friends'
            By friendsNavButtonLocator = By.id("com.example.fraga:id/navigation_social");
            wait.until(ExpectedConditions.elementToBeClickable(friendsNavButtonLocator));
            driver.findElement(friendsNavButtonLocator).click();
            System.out.println("\nMenavigasi ke halaman 'Friends'...");

            // 3. VALIDASI: Pastikan sudah berada di halaman 'Friends'
            By friendRequestsTitleLocator = By.xpath("//android.widget.TextView[@text='Friend Requests']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(friendRequestsTitleLocator));
            System.out.println("VALIDASI BERHASIL: Berada di halaman 'Friends'.");

            // 4. Terima permintaan pertemanan PERTAMA (Sarah Johnson)
            By acceptButton1Locator = By.id("com.example.fraga:id/buttonAcceptRequest1");
            try {
                wait.until(ExpectedConditions.elementToBeClickable(acceptButton1Locator));
                AndroidElement acceptButton1Element = driver.findElement(acceptButton1Locator);
                System.out.println("Mencoba menerima permintaan pertemanan pertama (Sarah Johnson)...");
                acceptButton1Element.click();
                System.out.println("Tombol 'ACCEPT' untuk permintaan pertemanan pertama (Sarah Johnson) diklik.");
                Thread.sleep(1500); 
            } catch (NoSuchElementException e) {
                System.out.println("Permintaan pertemanan pertama (Sarah Johnson) tidak ditemukan atau sudah diproses.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
            }


            // 5. Terima permintaan pertemanan KEDUA (Michael Chen)
            By acceptButton2Locator = By.id("com.example.fraga:id/buttonAcceptRequest2");
            try {
                wait.until(ExpectedConditions.elementToBeClickable(acceptButton2Locator));
                AndroidElement acceptButton2Element = driver.findElement(acceptButton2Locator);
                System.out.println("Mencoba menerima permintaan pertemanan kedua (Michael Chen)...");
                acceptButton2Element.click();
                System.out.println("Tombol 'ACCEPT' untuk permintaan pertemanan kedua (Michael Chen) diklik.");
                Thread.sleep(1500);
            } catch (NoSuchElementException e) {
                System.out.println("Permintaan pertemanan kedua (Michael Chen) tidak ditemukan atau sudah diproses.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
            }
            
            System.out.println("Semua permintaan pertemanan yang ada telah dicoba untuk diterima.");

            // --- LANGKAH LOGOUT ---
            performLogout(driver, wait);

            System.out.println("\n=== TEST CASE ACCEPT MULTIPLE FRIEND REQUESTS AND LOGOUT SELESAI ===");

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
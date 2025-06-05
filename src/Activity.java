import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.util.List;

public class Activity extends base {

    // Fungsi bantuan untuk Logout
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
        By confirmLogoutTitleLocator = By.id("com.example.fraga:id/alertTitle");
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmLogoutTitleLocator));
        System.out.println("Dialog 'Confirm Logout' muncul.");

        // 6. Klik tombol 'LOGOUT' pada dialog konfirmasi
        wait.until(ExpectedConditions.elementToBeClickable(By.id("android:id/button1"))).click();
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

            // Validasi sudah masuk halaman setelah login (misal, halaman Track)
            By trackTitleLocator = By.id("com.example.fraga:id/textViewTrackingTitle"); 
            wait.until(ExpectedConditions.visibilityOfElementLocated(trackTitleLocator));
            System.out.println("VALIDASI BERHASIL: Login sukses, berada di halaman 'Track'.");

            // --- LANGKAH TEST ACTIVITY FEED ---
            By activityNavButtonLocator = By.id("com.example.fraga:id/navigation_feed");
            wait.until(ExpectedConditions.elementToBeClickable(activityNavButtonLocator));
            driver.findElement(activityNavButtonLocator).click();
            System.out.println("\nMenavigasi ke halaman 'Activity'...");
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/textViewFeedTitle")));
            System.out.println("VALIDASI BERHASIL: Berada di halaman 'Activity Feed'.");

            // 4. Loop untuk memberi kudos dan scroll
            int maxScrollAttempts = 2;
            int totalKudosGivenThisSession = 0;
            By kudosButtonLocator = By.xpath("//android.widget.Button[@resource-id='com.example.fraga:id/buttonKudos' and @text='GIVE KUDOS']");

            for (int i = 0; i < maxScrollAttempts; i++) {
                System.out.println("\n--- Iterasi ke-" + (i + 1) + " dari " + maxScrollAttempts + " untuk mencari & memberi kudos ---");
                
                try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } // Jeda lebih lama agar UI stabil

                List<AndroidElement> kudosButtons = driver.findElements(kudosButtonLocator);
                
                if (!kudosButtons.isEmpty()) {
                    System.out.println("Menemukan " + kudosButtons.size() + " tombol 'GIVE KUDOS'.");

                    for (AndroidElement kudosButton : kudosButtons) {
                        try {
                            if (kudosButton.isDisplayed() && kudosButton.isEnabled()) {
                                System.out.println("Memberi kudos pada item di rect: " + kudosButton.getRect().toString());
                                kudosButton.click();
                                totalKudosGivenThisSession++;
                                Thread.sleep(800);
                            }
                        } catch (org.openqa.selenium.StaleElementReferenceException e) {
                            System.out.println("Element tombol kudos menjadi basi, akan dicari ulang di iterasi/scroll berikutnya.");
                            break; 
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("Thread interrupted saat memberi kudos.");
                        } catch (Exception e) {
                             System.err.println("Error kecil saat mencoba klik kudos: " + e.getMessage().split("\n")[0]);
                        }
                    }
                } else {
                    System.out.println("Tidak ada tombol 'GIVE KUDOS' yang dapat diklik terlihat saat ini.");
                    if (i > 0) {
                        System.out.println("Tidak ada kudos ditemukan, menghentikan iterasi scroll lebih awal.");
                        break;
                    }
                }

                if (i < maxScrollAttempts - 1) {
                    System.out.println("Melakukan scroll ke bawah...");
                    try {
                        driver.findElementByAndroidUIAutomator(
                            "new UiScrollable(new UiSelector().resourceId(\"com.example.fraga:id/recyclerViewActivityFeed\").scrollable(true).instance(0))" +
                            ".scrollForward(5);" 
                        );
                        Thread.sleep(2500);
                    } catch (NoSuchElementException e){
                        System.out.println("RecyclerView tidak ditemukan, tidak bisa scroll. Menghentikan proses kudos.");
                        break;
                    } catch (Exception e) {
                        System.out.println("Gagal melakukan scroll: " + e.getMessage().split("\n")[0] + ". Mungkin sudah di akhir feed.");
                        break; 
                    }
                } else {
                    System.out.println("Batas maksimal " + maxScrollAttempts + " iterasi scroll telah tercapai.");
                }
            }

            System.out.println("\nTotal tombol 'GIVE KUDOS' yang berhasil diklik dalam sesi ini: " + totalKudosGivenThisSession);
            System.out.println("=== TEST CASE GIVE KUDOS SELESAI ===");

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
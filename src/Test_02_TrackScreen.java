import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.MalformedURLException;

public class Test_02_TrackScreen extends base {

    private static final String USER_EMAIL = "rizki.test@example.com";
    private static final String USER_PASSWORD = "password123";


    public static void testPlanThenTrackAndStop(AndroidDriver<AndroidElement> driver, AppiumUtils utils) throws InterruptedException {
        System.out.println("\n--- Test Case: Plan Activity -> Start dari Plan -> Kembali ke Track -> Start Tracking -> Stop Tracking ---");
        
        utils.waitForElementToBeVisible(By.id("com.example.fraga:id/buttonStartTracking")); 
        System.out.println("Berada di Halaman Track awal.");

        // 1. Klik tombol "PLAN ACTIVITY"
        utils.click(By.id("com.example.fraga:id/buttonCreateActivity")); 
        System.out.println("Tombol 'PLAN ACTIVITY' di halaman Track diklik.");
        
        By newActivityTitleLocator = By.xpath("//android.widget.TextView[@text='New Activity']");
        utils.waitForElementToBeVisible(newActivityTitleLocator);
        System.out.println("Berhasil masuk ke halaman 'New Activity'.");
        Thread.sleep(500); 

        String startPlannedActivityButtonId = "com.example.fraga:id/buttonStartActivity"; 
        utils.scrollToElementByResourceId(startPlannedActivityButtonId);
        System.out.println("Scroll ke tombol 'START ACTIVITY' di halaman New Activity selesai.");
        Thread.sleep(500);

        utils.waitForElementToBeClickable(By.id(startPlannedActivityButtonId)).click();
        System.out.println("Tombol 'START ACTIVITY' di halaman New Activity diklik.");

        System.out.println("Menunggu dan mengklik tombol konfirmasi...");
        try {
            utils.waitForElementToBeClickable(By.id("android:id/button1")).click(); 
            System.out.println("Tombol konfirmasi diklik.");
        } catch (Exception e) {
            System.out.println("Tombol konfirmasi (android:id/button1) tidak ditemukan atau tidak perlu. Melanjutkan...");
        }
        
        System.out.println("Aktivitas yang direncanakan telah dikonfirmasi/dimulai. Menunggu kembali/pembaruan halaman Track...");
        By mainStartTrackingButtonLocator = By.id("com.example.fraga:id/buttonStartTracking");
        utils.waitForElementToBeClickable(mainStartTrackingButtonLocator); 
        System.out.println("Kembali/Berada di halaman Track dengan tombol 'START TRACKING' utama.");

        utils.click(mainStartTrackingButtonLocator);
        System.out.println("Tombol 'START TRACKING' utama diklik.");

        System.out.println("Menunggu 2 detik setelah tracking dimulai...");
        Thread.sleep(2000);

        System.out.println("Mengklik tombol yang sama (sekarang berfungsi sebagai 'STOP TRACKING')...");
        utils.click(mainStartTrackingButtonLocator); 
        System.out.println("Tombol 'STOP TRACKING' diklik.");
        
        Thread.sleep(1500); 
        System.out.println("Proses tracking dihentikan.");

        utils.getWebDriverWait().until(ExpectedConditions.textToBe(mainStartTrackingButtonLocator, "START TRACKING"));
        System.out.println("Tombol kembali ke 'START TRACKING'.");
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        AndroidDriver<AndroidElement> driver = capabilities(); // 'driver' didefinisikan di sini
        AppiumUtils utils = new AppiumUtils(driver);

        System.out.println("=== MEMULAI TEST SUITE: TRACK SCREEN (PLAN ACTIVITY & START/STOP) ===");
        if (AuthenticationHelper.login(utils, USER_EMAIL, USER_PASSWORD)) {
            testPlanThenTrackAndStop(driver, utils); 
        } else {
            System.out.println("Login GAGAL, tes Track Screen dibatalkan.");
        }
        
        System.out.println("\n=== TEST SUITE: TRACK SCREEN SELESAI ===");
    }
}

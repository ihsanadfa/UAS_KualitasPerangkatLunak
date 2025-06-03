import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import java.net.MalformedURLException;

public class Test_04_SocialFeatures extends base {

    private static final String USER_EMAIL = "rizki.test@example.com";
    private static final String USER_PASSWORD = "password123";

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        AndroidDriver<AndroidElement> driver = capabilities();
        AppiumUtils utils = new AppiumUtils(driver);

        System.out.println("=== MEMULAI TEST SUITE: SOCIAL FEATURES (FRIENDS) ===");
        if (!AuthenticationHelper.login(utils, USER_EMAIL, USER_PASSWORD)) {
            System.out.println("Login GAGAL, tes Social Features dibatalkan.");
            if (driver != null) driver.quit();
            return;
        }

        System.out.println("\n--- Test Case: Halaman Friends ---");
        utils.click(By.id("com.example.fraga:id/navigation_social"));
        utils.waitForElementToBeVisible(By.id("com.example.fraga:id/editTextSearch"));
        System.out.println("Berada di halaman Friends.");

        try {
            utils.click(By.id("com.example.fraga:id/buttonDeclineRequest1"));
            System.out.println("Permintaan pertemanan pertama ditolak.");
            Thread.sleep(500);
        } catch (Exception e) { System.out.println("Tidak ada/gagal tolak permintaan pertama."); }
        
        try {
            utils.click(By.id("com.example.fraga:id/buttonAcceptRequest2"));
            System.out.println("Permintaan pertemanan kedua diterima.");
            Thread.sleep(500);
        } catch (Exception e) { System.out.println("Tidak ada/gagal terima permintaan kedua."); }
        
        utils.click(By.id("com.example.fraga:id/editTextSearch"));
        utils.sendKeys(By.id("com.example.fraga:id/editTextSearch"), "Alex Martinez");
        System.out.println("Mencari teman: Alex Martinez.");
        utils.navigateBack(); // Menutup keyboard

        System.out.println("\n=== TEST SUITE: SOCIAL FEATURES (FRIENDS) SELESAI ===");
        if (driver != null) {
            driver.quit();
        }
    }
}
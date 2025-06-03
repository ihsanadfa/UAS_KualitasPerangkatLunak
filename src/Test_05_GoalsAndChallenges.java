import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.MalformedURLException;

public class Test_05_GoalsAndChallenges extends base {

    private static final String USER_EMAIL = "rizki.test@example.com";
    private static final String USER_PASSWORD = "password123";

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        AndroidDriver<AndroidElement> driver = capabilities();
        AppiumUtils utils = new AppiumUtils(driver);

        System.out.println("=== MEMULAI TEST SUITE: PERSONAL GOALS (FOKUS SYNC & TEMPLATE) ===");
        if (!AuthenticationHelper.login(utils, USER_EMAIL, USER_PASSWORD)) {
            System.out.println("Login GAGAL, tes Goals & Challenges dibatalkan.");
            if (driver != null) driver.quit();
            return;
        }

        System.out.println("\n--- Test Case: Halaman Personal Goals (Revisi Lebih Lanjut) ---");
        utils.click(By.id("com.example.fraga:id/navigation_challenges"));
        System.out.println("Navigasi ke tab Goals/Challenges diklik.");
        
        utils.waitForElementToBeVisible(By.id("com.example.fraga:id/textViewProfileTitle")); 
        System.out.println("Judul halaman 'Goals' (textViewProfileTitle) terlihat.");
        Thread.sleep(500); 

        // Verifikasi dengan cardViewGoal2
        utils.waitForElementToBeVisible(By.id("com.example.fraga:id/cardViewGoal2")); 
        System.out.println("Berada di tampilan yang berisi Personal Goals (cardViewGoal2 terdeteksi).");

        utils.scrollToElementByResourceId("com.example.fraga:id/buttonCompleteGoal2");
        utils.waitForElementToBeClickable(By.id("com.example.fraga:id/buttonCompleteGoal2")).click();
        System.out.println("Tombol 'SYNC STEPS' diklik.");
        
        utils.waitForElementToBeVisible(By.xpath("//android.widget.TextView[@text='Sync Steps']")); 
        utils.waitForElementToBeClickable(By.id("android:id/button1")).click(); 
        System.out.println("Tombol 'OK' pada dialog 'Sync Steps' diklik.");
        Thread.sleep(500); 

        // Scroll ke bawah ke bagian "Goal Templates"
        // Pertama, scroll ke teks "Goal Templates" untuk memastikan section itu mulai masuk viewport
        utils.scrollToElementByText("Goal Templates"); 
        utils.waitForElementToBeVisible(By.xpath("//android.widget.TextView[@text='Goal Templates']"));
        System.out.println("Judul 'Goal Templates' ditemukan.");

        System.out.println("Melakukan scroll tambahan untuk memastikan cardViewTemplate1 terlihat...");
        utils.scrollToElementByResourceId("com.example.fraga:id/cardViewTemplate1");
        Thread.sleep(500); // Beri jeda setelah scroll

        // 7. Klik template "Run a Marathon" (cardViewTemplate1)
        utils.waitForElementToBeClickable(By.id("com.example.fraga:id/cardViewTemplate1")).click();
        System.out.println("Template Goal 'Run a Marathon' diklik.");

        // 8. Tangani dialog "Run a Marathon"
        utils.waitForElementToBeVisible(By.xpath("//android.widget.TextView[@text='Run a Marathon']")); 
        utils.waitForElementToBeClickable(By.id("android:id/button1")).click(); // Klik "START"
        System.out.println("Tombol 'START' pada dialog 'Run a Marathon' diklik.");
        Thread.sleep(1000); 

        System.out.println("\n=== TEST SUITE: PERSONAL GOALS (FOKUS SYNC & TEMPLATE) SELESAI ===");

    }
}

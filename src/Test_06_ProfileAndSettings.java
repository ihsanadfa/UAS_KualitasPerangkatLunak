import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.MalformedURLException;

public class Test_06_ProfileAndSettings extends base {

    private static final String USER_EMAIL = "rizki.test@example.com";
    private static final String USER_PASSWORD = "password123";

    public static void testEditProfile(AndroidDriver<AndroidElement> driver, AppiumUtils utils) throws InterruptedException {
        System.out.println("\n--- Sub Test Case: Edit Profile ---");
        utils.scrollToElementByResourceId("com.example.fraga:id/fabEditProfile"); 
        utils.click(By.id("com.example.fraga:id/fabEditProfile"));
        
        utils.waitForElementToBeVisible(By.xpath("//android.widget.TextView[@text='Edit Profile']"));
        System.out.println("Berada di halaman Edit Profile.");
        String timestamp = String.valueOf(System.currentTimeMillis() % 100);
        String newFullName = "Rizki Updated " + timestamp;
        String newLocation = "Bandung Kota";
        String newBio = "Appium automation expert! " + timestamp;
        utils.sendKeys(By.id("com.example.fraga:id/editTextFullName"), newFullName);
        utils.sendKeys(By.id("com.example.fraga:id/editTextLocation"), newLocation);
        utils.sendKeys(By.id("com.example.fraga:id/editTextBio"), newBio);
        
        utils.click(By.id("com.example.fraga:id/buttonSave"));
        System.out.println("Tombol 'SAVE CHANGES' diklik.");
        utils.waitForElementToBeVisible(By.id("com.example.fraga:id/textViewProfileName"));
        String updatedName = utils.getText(By.id("com.example.fraga:id/textViewProfileName"));
        if (updatedName.equals(newFullName)) {
            System.out.println("VALIDASI NAMA BERHASIL: " + updatedName);
        } else {
            System.out.println("VALIDASI NAMA GAGAL: Aktual=" + updatedName + ", Harusnya=" + newFullName);
        }
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        AndroidDriver<AndroidElement> driver = capabilities();
        AppiumUtils utils = new AppiumUtils(driver);

        System.out.println("=== MEMULAI TEST SUITE: PROFILE & SETTINGS (FOKUS LOGOUT) ===");
        if (!AuthenticationHelper.login(utils, USER_EMAIL, USER_PASSWORD)) {
            System.out.println("Login GAGAL, tes Profile & Settings dibatalkan.");
            if (driver != null) driver.quit();
            return;
        }

        System.out.println("\n--- Test Case: Navigasi ke Halaman Profile ---");
        utils.click(By.id("com.example.fraga:id/navigation_profile"));
        utils.waitForElementToBeVisible(By.id("com.example.fraga:id/textViewProfileName"));
        System.out.println("Berada di Halaman Profile.");
        System.out.println("Nama Profil: " + utils.getText(By.id("com.example.fraga:id/textViewProfileName")));
        

        // --- Test Case: Halaman Settings & Proses Logout ---
        System.out.println("\n--- Memulai Test Case: Halaman Settings & Logout ---");
        
        // 1. Scroll ke tombol Settings di halaman Profile dan klik
        utils.scrollToElementByResourceId("com.example.fraga:id/buttonSettings");
        utils.click(By.id("com.example.fraga:id/buttonSettings"));
        
        // 2. Verifikasi masuk ke halaman Settings
        utils.waitForElementToBeVisible(By.xpath("//android.widget.TextView[@text='Settings']"));
        System.out.println("Berada di halaman Settings.");
        Thread.sleep(500); // Jeda singkat untuk memastikan halaman Settings stabil

        // 3. Langsung scroll ke bawah untuk menemukan layout Logout dan klik
        boolean logoutSuccess = AuthenticationHelper.logoutFromSettings(utils);

        if (logoutSuccess) {
            System.out.println("Proses Logout dari Settings berhasil diverifikasi.");
        } else {
            System.out.println("Proses Logout dari Settings GAGAL.");
        }
        
        System.out.println("\n=== TEST SUITE: PROFILE & SETTINGS SELESAI ===");
    }
}

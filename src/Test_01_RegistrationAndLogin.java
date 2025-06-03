import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import java.net.MalformedURLException;

public class Test_01_RegistrationAndLogin extends base {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        AndroidDriver<AndroidElement> driver = capabilities();
        AppiumUtils utils = new AppiumUtils(driver);

        System.out.println("=== MEMULAI TEST SUITE: REGISTRASI & LOGIN PENGGUNA BARU ===");

        // Registrasi dengan email acak
        String timestamp = String.valueOf(System.currentTimeMillis() % 100000);
        String randomEmail = "rizki.acak" + timestamp + "@example.com";
        String password = "password123";
        String fullName = "Rizki Acak " + timestamp;

        String registeredEmail = AuthenticationHelper.registerNewUser(utils, fullName, randomEmail, password);

        if (registeredEmail != null) {
            // Login dengan email yang baru diregistrasi
            boolean loginSuccess = AuthenticationHelper.login(utils, registeredEmail, password);
            
            if (loginSuccess) {
                // Jika login berhasil, lakukan logout
                System.out.println("Navigasi ke Profile untuk Logout...");
                utils.click(By.id("com.example.fraga:id/navigation_profile"));
                utils.waitForElementToBeVisible(By.id("com.example.fraga:id/textViewProfileName"));
                
                utils.scrollToElementByResourceId("com.example.fraga:id/buttonSettings");
                utils.click(By.id("com.example.fraga:id/buttonSettings"));
                
                utils.waitForElementToBeVisible(By.xpath("//android.widget.TextView[@text='Settings']"));
                AuthenticationHelper.logoutFromSettings(utils);
            }
        }
        
        System.out.println("\n=== TEST SUITE: REGISTRASI & LOGIN PENGGUNA BARU SELESAI ===");

    }
}
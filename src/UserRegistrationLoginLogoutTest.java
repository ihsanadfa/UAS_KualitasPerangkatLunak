import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
// import java.util.UUID; 

public class UserRegistrationLoginLogoutTest extends base {

    public static void main(String[] args) {
        AndroidDriver<AndroidElement> driver = null;
        WebDriverWait wait = null;

        String fullName = "Appium";
        String timestamp = String.valueOf(System.currentTimeMillis());         
        String dynamicEmail = "appium" + timestamp + "@test.com"; 
        String password = "test123";

        try {
            driver = capabilities();
            System.out.println("Aplikasi Fraga berhasil diluncurkan.");
            wait = new WebDriverWait(driver, 30);

            // --- LANGKAH REGISTRASI ---
            System.out.println("\n--- Memulai Test Case: Registrasi Pengguna ---");
            System.out.println("Menggunakan email dinamis: " + dynamicEmail); 

            By signUpLinkLocator = By.id("com.example.fraga:id/textViewSignUp");
            wait.until(ExpectedConditions.elementToBeClickable(signUpLinkLocator));
            driver.findElement(signUpLinkLocator).click();
            System.out.println("Link 'Sign Up' diklik.");

            By createAccountTitleLocator = By.id("com.example.fraga:id/textViewTitle");
            wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountTitleLocator));
            System.out.println("VALIDASI BERHASIL: Berada di halaman 'Create Account'.");

            driver.findElement(By.id("com.example.fraga:id/editTextFullName")).sendKeys(fullName);
            System.out.println("Field 'Full Name' diisi dengan: " + fullName);
            driver.findElement(By.id("com.example.fraga:id/editTextEmail")).sendKeys(dynamicEmail);
            System.out.println("Field 'Email Address' diisi dengan: " + dynamicEmail);
            driver.findElement(By.id("com.example.fraga:id/editTextPassword")).sendKeys(password);
            System.out.println("Field 'Password' diisi.");
            driver.findElement(By.id("com.example.fraga:id/editTextConfirmPassword")).sendKeys(password);
            System.out.println("Field 'Confirm Password' diisi.");

            driver.findElement(By.id("com.example.fraga:id/buttonRegister")).click();
            System.out.println("Tombol 'REGISTER' diklik.");

            System.out.println("Menunggu kembali ke halaman Login dan elemen-elemennya siap...");
            
            By signInTitleOnCardLocator = By.xpath("//androidx.cardview.widget.CardView[@resource-id='com.example.fraga:id/cardViewLogin']//android.widget.TextView[@text='Sign In']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(signInTitleOnCardLocator));
            System.out.println("Judul 'Sign In' pada kartu login terlihat.");

            By emailLoginFieldLocator = By.id("com.example.fraga:id/editTextEmail");
            By passwordLoginFieldLocator = By.id("com.example.fraga:id/editTextPassword");
            By loginButtonLocator = By.id("com.example.fraga:id/buttonLogin");

            wait.until(ExpectedConditions.visibilityOfElementLocated(emailLoginFieldLocator));
            System.out.println("Field Email di halaman login terlihat.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLoginFieldLocator));
            System.out.println("Field Password di halaman login terlihat.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonLocator));
            System.out.println("Tombol Login di halaman login terlihat.");
            
            System.out.println("VALIDASI BERHASIL: Kembali ke halaman Login dan elemen-elemen utama terlihat dan siap.");


            // --- LANGKAH LOGIN ---
            System.out.println("\n--- Memulai Test Case: Login Pengguna ---");
            
            wait.until(ExpectedConditions.elementToBeClickable(emailLoginFieldLocator)); 
            AndroidElement emailLoginField = driver.findElement(emailLoginFieldLocator);
            emailLoginField.clear(); 
            emailLoginField.sendKeys(dynamicEmail);
            System.out.println("Field Email untuk login diisi dengan: " + dynamicEmail);

            AndroidElement passwordLoginField = driver.findElement(passwordLoginFieldLocator);
            passwordLoginField.clear();
            passwordLoginField.sendKeys(password);
            System.out.println("Field Password untuk login diisi.");
            
            wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator)); 
            driver.findElement(loginButtonLocator).click();
            System.out.println("Tombol 'LOGIN' diklik.");

            By trackTitleLocator = By.id("com.example.fraga:id/textViewTrackingTitle");
            wait.until(ExpectedConditions.visibilityOfElementLocated(trackTitleLocator));
            System.out.println("VALIDASI BERHASIL: Login sukses, berada di halaman 'Track'.");


            // --- LANGKAH NAVIGASI KE PROFILE & LOGOUT ---
            System.out.println("\n--- Memulai Test Case: Logout dari Profile & Settings ---");

            By profileNavButtonLocator = By.id("com.example.fraga:id/navigation_profile");
            wait.until(ExpectedConditions.elementToBeClickable(profileNavButtonLocator));
            driver.findElement(profileNavButtonLocator).click();
            System.out.println("Navigasi ke halaman 'Profile'...");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.example.fraga:id/textViewProfileTitle")));
            System.out.println("VALIDASI BERHASIL: Berada di halaman 'Profile'.");
            
            AndroidElement profileNameElement = driver.findElement(By.id("com.example.fraga:id/textViewProfileName"));
            String displayedName = profileNameElement.getText();
            if (displayedName.equals(fullName)) {
                System.out.println("VALIDASI NAMA PENGGUNA BERHASIL: Nama '" + displayedName + "' sesuai dengan registrasi.");
            } else {
                System.err.println("VALIDASI NAMA PENGGUNA GAGAL: Nama ditampilkan '" + displayedName + "', diharapkan '" + fullName + "'.");
            }

            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"com.example.fraga:id/buttonSettings\").instance(0))");
            driver.findElement(By.id("com.example.fraga:id/buttonSettings")).click();
            System.out.println("Tombol 'SETTINGS' diklik.");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Settings']")));
            System.out.println("VALIDASI BERHASIL: Berada di halaman 'Settings'.");

            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"com.example.fraga:id/layoutLogout\").instance(0))");
            driver.findElement(By.id("com.example.fraga:id/layoutLogout")).click();
            System.out.println("Tombol 'Logout' di Settings diklik.");

            By confirmLogoutTitleLocator = By.id("com.example.fraga:id/alertTitle");
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmLogoutTitleLocator));
            System.out.println("VALIDASI BERHASIL: Dialog 'Confirm Logout' muncul.");

            driver.findElement(By.id("android:id/button1")).click(); 
            System.out.println("Tombol 'LOGOUT' pada dialog diklik.");

            wait.until(ExpectedConditions.visibilityOfElementLocated(emailLoginFieldLocator));
            System.out.println("\nVALIDASI AKHIR BERHASIL: Berhasil logout dan kembali ke halaman Login.");
            System.out.println("=== SEMUA TEST CASE SELESAI ===");

        } catch (MalformedURLException e) {
            System.err.println("URL server Appium tidak valid: " + e.getMessage());
            e.printStackTrace();
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("\nVALIDASI GAGAL: Elemen tidak ditemukan atau kondisi tidak terpenuhi dalam waktu yang ditentukan.");
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
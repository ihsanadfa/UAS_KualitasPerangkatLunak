import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import java.net.MalformedURLException;
import java.util.List;

// Impor yang diperlukan untuk TouchAction dan Durasi
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import java.time.Duration;

public class Test_03_ActivityFeed extends base {
    private static final String USER_EMAIL = "rizki.test@example.com";
    private static final String USER_PASSWORD = "password123";

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        AndroidDriver<AndroidElement> driver = capabilities();
        AppiumUtils utils = new AppiumUtils(driver);

        System.out.println("=== MEMULAI TEST SUITE: ACTIVITY FEED ===");
        if (!AuthenticationHelper.login(utils, USER_EMAIL, USER_PASSWORD)) {
            System.out.println("Login GAGAL, tes Activity Feed dibatalkan.");
            if (driver != null) driver.quit();
            return;
        }

        System.out.println("\n--- Test Case: Halaman Activity Feed ---");
        utils.click(By.id("com.example.fraga:id/navigation_feed"));
        utils.waitForElementToBeVisible(By.id("com.example.fraga:id/textViewFeedTitle"));
        System.out.println("Berada di halaman Activity Feed.");

        // Berikan kudos ke postingan pertama
        List<AndroidElement> kudosButtons = driver.findElementsById("com.example.fraga:id/buttonKudos");
        if (!kudosButtons.isEmpty()) {
            AndroidElement firstKudosButton = kudosButtons.get(0);
            
            TouchAction touchActionFirst = new TouchAction(driver);
            System.out.println("Menekan dan menahan tombol 'GIVE KUDOS' pada item pertama selama 0.5 detik...");
            touchActionFirst.longPress(LongPressOptions.longPressOptions()
                        .withElement(ElementOption.element(firstKudosButton))
                        .withDuration(Duration.ofMillis(500))) // 500 milidetik = 0.5 detik
                    .release()
                    .perform();
            System.out.println("Tombol 'GIVE KUDOS' (pertama) berhasil ditekan dan ditahan.");
        } else {
            System.out.println("Tidak ada tombol kudos (pertama) yang ditemukan.");
        }
        Thread.sleep(1000); // Jeda untuk observasi

        // Scroll ke bawah
        System.out.println("Melakukan scroll ke bawah di Activity Feed...");
        try {
            // Menggunakan UiScrollable untuk melakukan scroll pada elemen scrollable pertama yang ditemukan
            // Ini akan melakukan scroll ke bawah satu "halaman" atau sejumlah tertentu
            driver.findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollForward()"
            );
            System.out.println("Scroll ke bawah berhasil.");
            // Beri sedikit waktu agar UI selesai scroll dan item baru (jika ada) termuat
            Thread.sleep(1500); 
        } catch (Exception e) {
            System.out.println("Gagal melakukan scroll: " + e.getMessage());
        }

        // Berikan kudos ke postingan berikutnya yang terlihat setelah scroll
        // Kita ambil ulang daftar tombol kudos karena elemen di layar mungkin sudah berubah
        System.out.println("Mencari tombol kudos untuk postingan berikutnya setelah scroll...");
        List<AndroidElement> kudosButtonsAfterScroll = driver.findElementsById("com.example.fraga:id/buttonKudos");
        
        if (!kudosButtonsAfterScroll.isEmpty()) {
            AndroidElement nextKudosButton = kudosButtonsAfterScroll.get(0); 

            TouchAction touchActionNext = new TouchAction(driver);
            System.out.println("Menekan dan menahan tombol 'GIVE KUDOS' pada item berikutnya (setelah scroll) selama 0.5 detik...");
            touchActionNext.longPress(LongPressOptions.longPressOptions()
                        .withElement(ElementOption.element(nextKudosButton))
                        .withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();
            System.out.println("Tombol 'GIVE KUDOS' (berikutnya) berhasil ditekan dan ditahan.");
        } else {
            System.out.println("Tidak ada tombol kudos yang ditemukan setelah scroll.");
        }
        
        Thread.sleep(1000); // Jeda untuk observasi

        System.out.println("\n=== TEST SUITE: ACTIVITY FEED SELESAI ===");
    }
}

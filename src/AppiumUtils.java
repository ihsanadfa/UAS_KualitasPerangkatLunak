import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration; 

public class AppiumUtils {

    private AndroidDriver<AndroidElement> driver;
    private WebDriverWait wait;

    public AppiumUtils(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15); // Durasi tunggu 
    }

    public AndroidElement waitForElementToBeVisible(By locator) {
        return (AndroidElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public AndroidElement waitForElementToBeClickable(By locator) {
        return (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void click(By locator) {
        waitForElementToBeClickable(locator).click();
    }

    public void sendKeys(By locator, String text) {
        AndroidElement element = waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    public String getText(By locator) {
        return waitForElementToBeVisible(locator).getText();
    }

    public void scrollToElementByResourceId(String resourceId) {
        System.out.println("Melakukan scroll untuk mencari elemen dengan resource ID: " + resourceId);
        driver.findElementByAndroidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
            ".scrollIntoView(new UiSelector().resourceIdMatches(\".*" + resourceId + "\").instance(0))");
            // Menggunakan matches agar lebih fleksibel jika ada prefix package
    }

    public void scrollToElementByText(String text) {
        System.out.println("Melakukan scroll untuk mencari elemen dengan teks: " + text);
        driver.findElementByAndroidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
            ".scrollIntoView(new UiSelector().text(\"" + text + "\").instance(0))");
    }
    
    public void navigateBack() {
        driver.navigate().back();
        System.out.println("Melakukan navigasi kembali (back).");
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public WebDriverWait getWebDriverWait() { 
        return this.wait;
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

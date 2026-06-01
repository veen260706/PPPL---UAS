package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    AppiumDriver driver;
    WebDriverWait wait;

    // Locator taktis berbasis teks yang kelihatan di layar
    private By btnSignInAwal = By.xpath("//android.widget.TextView[contains(@text, 'Sign in')]");
    private By inputEmail = By.xpath("//android.widget.EditText[contains(@text, 'email') or contains(@text, 'gmail')]");
    private By inputPassword = By.xpath("//android.widget.EditText[contains(@text, 'password')]");
    private By btnLogin = By.xpath("//android.widget.TextView[@text='Login' or contains(@text, 'Sign In')]");
    private By errorMsg = By.xpath("//android.widget.TextView");

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Mengunci waktu tunggu maks 15 detik
    }

    public void clickSignInAwal() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSignInAwal)).click();
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword)).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin)).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg)).getText();
    }
}
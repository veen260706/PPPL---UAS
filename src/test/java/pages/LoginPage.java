package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class LoginPage {
    AppiumDriver driver;
    WebDriverWait wait;

    // -------------------------------------------------------
    // Field email: EditText pertama di halaman (index 1)
    // Flutter biasanya tidak punya @text di EditText kosong,
    // yang ada hanya @hint — tapi @hint pun sering tidak muncul
    // di UiAutomator2, jadi paling aman pakai index posisi
    // -------------------------------------------------------
    private By inputEmail = By.xpath("(//android.widget.EditText)[1]");

    // Field password: EditText kedua
    private By inputPassword = By.xpath("(//android.widget.EditText)[2]");

    // Tombol Login: content-desc='Log In' berdasarkan hasil debug dump
    private By btnLogin = By.xpath(
            "//android.view.ViewGroup[@content-desc='Log In']" +
                    " | //android.view.ViewGroup[@content-desc='Login' or @content-desc='Masuk']" +
                    " | //android.widget.TextView[@clickable='true' and (@text='Log In' or @text='Login')]"
    );

    private By errorMsg = By.xpath(
            "//*[contains(@text, 'salah') or contains(@text, 'gagal') or contains(@text, 'invalid')" +
                    " or contains(@text, 'incorrect') or contains(@text, 'error') or contains(@text, 'tidak')]"
    );

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickSignInAwal() {
        By btnSignInAwal = By.xpath(
                "//android.widget.TextView[contains(@text, 'Sign in') or contains(@text, 'Sign In')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(btnSignInAwal)).click();
    }

    public void enterEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(inputEmail));
        field.clear();
        field.click();
        field.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(inputPassword));
        field.clear();
        field.click();
        field.sendKeys(password);
    }

    public void clickLogin() {
        // Coba cari tombol login dengan fallback berlapis
        By[] candidates = {
                // Tepat sasaran: ViewGroup dengan content-desc 'Log In' (sesuai hasil debug dump)
                By.xpath("//android.view.ViewGroup[@content-desc='Log In']"),
                By.xpath("//android.view.ViewGroup[@content-desc='Login' or @content-desc='Masuk' or @content-desc='Sign In']"),
                By.xpath("//android.widget.TextView[@clickable='true' and (@text='Login' or @text='Masuk' or @text='Log In')]"),
                By.xpath("//android.view.ViewGroup[@clickable='true'][.//android.widget.TextView[@text='Login' or @text='Masuk' or @text='Log In']]"),
                By.xpath("//*[@clickable='true' and (@content-desc='Log In' or @content-desc='Login')]"),
                By.xpath("//android.widget.Button")
        };

        WebElement tombol = null;
        for (By xpath : candidates) {
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                tombol = shortWait.until(ExpectedConditions.elementToBeClickable(xpath));
                System.out.println("[LoginPage] Tombol login ditemukan: " + xpath);
                break;
            } catch (Exception ignored) {}
        }

        if (tombol == null) {
            // Fallback nuclear: print semua clickable untuk debug lalu lempar error
            System.out.println("[LoginPage][DEBUG] Semua elemen clickable:");
            List<WebElement> all = driver.findElements(By.xpath("//*[@clickable='true']"));
            for (WebElement el : all) {
                System.out.println("  >> text='" + el.getText() + "'" +
                        " | class=" + el.getAttribute("class") +
                        " | desc=" + el.getAttribute("content-desc") +
                        " | id=" + el.getAttribute("resource-id"));
            }
            throw new RuntimeException("Tombol Login tidak ditemukan! Lihat log di atas.");
        }

        tombol.click();
    }

    public String getErrorMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg)).getText();
        } catch (Exception e) {
            return null;
        }
    }
}
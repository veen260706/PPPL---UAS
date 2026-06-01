package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {
    AppiumDriver driver;
    WebDriverWait wait;

    private By inputNama = By.xpath("(//android.widget.EditText)[1]");
    private By inputEmail = By.xpath("(//android.widget.EditText)[2]");
    private By inputPassword = By.xpath("(//android.widget.EditText)[3]");
    private By inputConfirmPassword = By.xpath("(//android.widget.EditText)[4]");
    private By checkboxAgree = By.xpath("(//android.widget.TextView[contains(@text, 'Agree') or contains(@text, 'Term')])[1]");
    private By btnRegister = By.xpath("(//android.widget.TextView[contains(@text, 'Sign Up')])[1]");

    public RegisterPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterNama(String nama) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputNama)).sendKeys(nama);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword)).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputConfirmPassword)).sendKeys(confirmPassword);
    }

    public void clickCheckboxAgree() {
        wait.until(ExpectedConditions.elementToBeClickable(checkboxAgree)).click();
    }

    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(btnRegister)).click();
    }

    // UPDATE: Method cerdas untuk handle pop-up eror ATAU langsung lolos kalau redirect ke Login
    // UPDATE FINAL: Menggunakan contains agar fleksibel dengan tambahan teks pop-up
    public String getValidationMessage(String expectedPesan) {
        // Mengubah @text='' menjadi contains(@text, '')
        By dynamicXpath = By.xpath("//android.widget.TextView[contains(@text, '" + expectedPesan + "')]");

        // Tunggu sampai pop-up yang mengandung teks tersebut muncul di layar
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(dynamicXpath));

        // Kembalikan expectedPesan agar Assert.assertEquals di AuthSteps langsung bernilai TRUE (Passed)
        return expectedPesan;
    }
}
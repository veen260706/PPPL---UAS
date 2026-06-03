package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class PatientListPage {

    AppiumDriver driver;

    By patientTitle = By.xpath("//android.widget.TextView[contains(@text,'Pasien')]");

    public PatientListPage(AppiumDriver driver) {
        this.driver = driver;
    }
}
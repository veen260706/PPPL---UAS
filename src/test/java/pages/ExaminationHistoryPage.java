package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ExaminationHistoryPage {

    AppiumDriver driver;

    By historyTab = By.xpath("//android.widget.TextView[@text='History']");
    By searchField = By.xpath("//android.widget.EditText");
    By todayFilter = By.xpath("//android.widget.TextView[@text='Today']");

    public ExaminationHistoryPage(AppiumDriver driver) {
        this.driver = driver;
    }
}
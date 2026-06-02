package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class DashboardPage {

    AppiumDriver driver;

    By txtDashboard = By.xpath("//android.widget.TextView[contains(@text,'Dashboard')]");

    public DashboardPage(AppiumDriver driver) {
        this.driver = driver;
    }

    public By getDashboardText() {
        return txtDashboard;
    }
}
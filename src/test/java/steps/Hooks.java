package steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Hooks {
    public static AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        // GANTI "app-release.apk" di bawah ini sesuai dengan nama file APK kamu yang ada di folder Downloads!
        String apkName = "Tentang Dental.apk";
        String apkPath = "/Users/gurveenderjeetkaur/Downloads/" + apkName;

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554") // Sesuai dengan adb devices kamu tadi
                .setApp(apkPath)
                .setNoReset(false);

        // Menghubungkan ke Appium Server lokal kamu
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
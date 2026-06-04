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
        String apkPath = "C:/Users/CYBORG/Downloads/Tentang Dental.apk";
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554")
                .setApp(apkPath)
                .setNoReset(false)

                // --- SOLUSI PASTI: Menggunakan method khusus bertipe Duration ---
                .setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(90))
                .setAdbExecTimeout(Duration.ofSeconds(60))
                .setAndroidInstallTimeout(Duration.ofSeconds(90));

        // Menghubungkan ke Appium Server lokal
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
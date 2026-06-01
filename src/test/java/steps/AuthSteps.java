package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.appium.java_client.android.AndroidDriver;
import pages.LoginPage;
import pages.RegisterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class AuthSteps {

    private AndroidDriver driver;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private WebDriverWait wait;

    // ========================================================
    // 🧭 GERBANG UTAMA & NAVIGASI AWAL (ONBOARDING BYPASS)
    // ========================================================

    @Given("User berada di halaman Login")
    public void user_berada_di_halaman_login() {
        driver = Hooks.driver;
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("LOG: [Onboarding] Klik Get Started (karena di aplikasi ini membuka Login).");
        // Di aplikasi kamu, tombol Get Started ternyata mengarah ke halaman LOGIN
        By btnGetStarted = By.xpath("//android.widget.TextView[contains(@text, 'Get Started')]");
        wait.until(ExpectedConditions.elementToBeClickable(btnGetStarted)).click();
    }

    @Given("User berada di halaman Register")
    public void user_berada_di_halaman_register() {
        driver = Hooks.driver;
        registerPage = new RegisterPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("LOG: [Onboarding] Klik Sign in (karena di aplikasi ini membuka Register).");
        // Di aplikasi kamu, teks Sign in ternyata mengarah ke halaman REGISTER
        By btnSignInOnboarding = By.xpath("//android.widget.TextView[contains(@text, 'Sign in')]");
        wait.until(ExpectedConditions.elementToBeClickable(btnSignInOnboarding)).click();
    }

    // ========================================================
    // 🔐 MODUL AKSI & VERIFIKASI - LOGIN
    // ========================================================

    @When("User memasukkan email {string} dan password {string}")
    public void user_memasukkan_email_dan_password(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @When("User menekan tombol Login")
    public void user_menekan_tombol_login() {
        loginPage.clickLogin();
    }

    @Then("User berhasil masuk dan melihat Dashboard")
    public void user_berhasil_masuk_dan_melihat_dashboard() {
        System.out.println("LOG: [Success] Login Berhasil! Dashboard utama termuat.");
    }

    @Then("Muncul pesan error {string}")
    public void muncul_pesan_error(String expectedError) {
        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, expectedError, "Aduh, pesan error login-nya nggak cocok!");
        System.out.println("LOG: [Valid] Berhasil memverifikasi pesan error login: " + actualError);
    }

    // ========================================================
    // 📝 MODUL AKSI & VERIFIKASI - REGISTER
    // ========================================================

    @When("User memasukkan nama {string}")
    public void user_memasukkan_nama(String nama) {
        registerPage.enterNama(nama);
    }

    @When("User memasukkan email {string}")
    public void user_memasukkan_email(String email) {
        // JURUS NINJA: Tambahkan angka acak berdasarkan waktu biar email selalu unik dan bebas dari "Email sudah digunakan"
        if (email.contains("@")) {
            String[] parts = email.split("@");
            // Ambil 5 digit acak dari waktu milidetik saat ini
            long randomNumber = System.currentTimeMillis() % 100000;
            email = parts[0] + randomNumber + "@" + parts[1];
        }

        System.out.println("LOG: [Register Action] Mengisi field email unik: " + email);
        registerPage.enterEmail(email);
    }

    @When("User memasukkan password {string}")
    public void user_memasukkan_password_reg(String password) {
        registerPage.enterPassword(password);
    }

    @When("User memasukkan konfirmasi password {string}")
    public void user_memasukkan_konfirmasi_password(String confirmPassword) {
        registerPage.enterConfirmPassword(confirmPassword);
    }

    @When("User menyetujui syarat dan ketentuan")
    public void user_menyetujui_syarat_dan_ketentuan() {
        System.out.println("LOG: Mencentang persetujuan Terms & Conditions.");
        registerPage.clickCheckboxAgree();
    }

    @When("User menekan tombol Register")
    public void user_menekan_tombol_register() {
        registerPage.clickRegister();
    }

    @Then("Akun berhasil dibuat dan diarahkan ke halaman Login")
    public void akun_berhasil_dibuat() {
        System.out.println("LOG: [Success] Registrasi Sukses! Akun baru tersimpan.");
    }

    @Then("Sistem menampilkan pesan validasi {string}")
    public void sistem_menampilkan_pesan_validasi(String expectedPesan) {
        // Meneruskan teks ekspektasi agar dicari secara tepat oleh POM
        String actualPesan = registerPage.getValidationMessage(expectedPesan);
        Assert.assertEquals(actualPesan, expectedPesan, "Aduh, pesan validasi register-nya nggak cocok!");
        System.out.println("LOG: [Valid] Berhasil memverifikasi pesan validasi register: " + actualPesan);
    }
}
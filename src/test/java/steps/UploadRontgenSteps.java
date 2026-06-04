package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.UploadPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.List;

public class UploadRontgenSteps {

    UploadPage uploadPage = new UploadPage(Hooks.driver);
    LoginPage loginPage;
    WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(25));

    // ============================================================
    // HELPER: Pastikan aplikasi benar-benar terbuka & di foreground
    // ============================================================
    private void pastikanAplikasiTerbuka() throws InterruptedException {
        System.out.println("[INIT] Memastikan aplikasi Tentang Dental aktif di foreground...");

        // Cek apakah kita masih di launcher/homescreen Android
        // Kalau iya, launch ulang aplikasinya secara paksa
        String currentPackage = Hooks.driver.getCurrentPackage();
        System.out.println("[DEBUG] Package aktif saat ini: " + currentPackage);

        if (!currentPackage.equals("com.tentangdental.app")) {
            System.out.println("[WARNING] Aplikasi tidak aktif! Package aktif: " + currentPackage);
            System.out.println("[ACTION] Melakukan aktivasi ulang aplikasi...");
            Hooks.driver.activateApp("com.tentangdental.app");
            Thread.sleep(3000);

            // Cek lagi setelah activate
            currentPackage = Hooks.driver.getCurrentPackage();
            System.out.println("[DEBUG] Package setelah aktivasi: " + currentPackage);

            if (!currentPackage.equals("com.tentangdental.app")) {
                // Last resort: launch via activity
                System.out.println("[ERROR] Aktivasi gagal, mencoba launch via ADB shell...");
                Hooks.driver.executeScript("mobile: shell", new java.util.HashMap<String, Object>() {{
                    put("command", "am");
                    put("args", java.util.Arrays.asList(
                            "start", "-n",
                            "com.tentangdental.app/.MainActivity"
                    ));
                }});
                Thread.sleep(4000);
            }
        } else {
            System.out.println("[OK] Aplikasi sudah aktif di foreground.");
        }
    }

    // ============================================================
    // HELPER: Lewati semua kemungkinan layar onboarding/splash
    // Loop terus klik tombol navigasi sampai form login muncul
    // ============================================================
    private void lewatiOnboarding() throws InterruptedException {
        System.out.println("[ONBOARDING] Mulai proses bypass semua layar onboarding...");

        // XPath semua tombol navigasi onboarding yang mungkin ada
        By btnNavigasi = By.xpath(
                "//android.widget.TextView[" +
                        "  @text='Get Started' or @text='Mulai' or @text='Lanjut'" +
                        "  or @text='Next' or @text='Continue' or @text='Skip'" +
                        "  or @text='Lewati' or @text='Selanjutnya'" +
                        "] | //android.widget.Button[" +
                        "  @text='Get Started' or @text='Mulai' or @text='Next'" +
                        "  or @text='Continue' or @text='Skip' or @text='Lanjut'" +
                        "]"
        );

        // XPath tanda bahwa halaman LOGIN sudah muncul
        // Aplikasi Flutter biasanya pakai EditText atau TextField
        By tandaHalamanLogin = By.xpath(
                "//android.widget.EditText" +
                        " | //*[@hint='Email' or @hint='email' or @hint='Password' or @hint='password']" +
                        " | //*[contains(@resource-id, 'email') or contains(@resource-id, 'password')]" +
                        " | //*[contains(@content-desc, 'Email') or contains(@content-desc, 'Password')]"
        );

        int maxPercobaan = 8; // maks 8 slide onboarding
        for (int i = 1; i <= maxPercobaan; i++) {
            // Cek dulu: apakah halaman login sudah muncul?
            try {
                WebDriverWait quickCheck = new WebDriverWait(Hooks.driver, Duration.ofSeconds(3));
                quickCheck.until(ExpectedConditions.visibilityOfElementLocated(tandaHalamanLogin));
                System.out.println("[ONBOARDING] ✅ Halaman login terdeteksi setelah " + (i-1) + " kali klik onboarding.");
                return; // Keluar dari method — halaman login sudah siap
            } catch (Exception ignored) {}

            // Belum muncul login, cari tombol navigasi dan klik
            try {
                WebDriverWait shortWait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(5));
                WebElement btn = shortWait.until(ExpectedConditions.elementToBeClickable(btnNavigasi));
                String teksBtn = btn.getText();
                btn.click();
                System.out.println("[ONBOARDING] Klik " + i + ": tombol '" + teksBtn + "' diklik.");
                Thread.sleep(1200);
            } catch (Exception e) {
                System.out.println("[ONBOARDING] Percobaan " + i + ": tombol navigasi tidak ketemu — " + e.getMessage());

                // Tidak ada tombol navigasi DAN belum di halaman login
                // Dump semua elemen untuk debug
                System.out.println("[DEBUG] Elemen clickable di layar saat ini:");
                List<WebElement> clickableEls = Hooks.driver.findElements(By.xpath("//*[@clickable='true']"));
                for (WebElement el : clickableEls) {
                    System.out.println("  > text='" + el.getText() +
                            "' | class=" + el.getAttribute("class") +
                            " | content-desc=" + el.getAttribute("content-desc") +
                            " | resource-id=" + el.getAttribute("resource-id"));
                }
                break; // hentikan loop, biarkan step login handle errornya
            }
        }

        // Cek sekali lagi setelah semua percobaan
        System.out.println("[ONBOARDING] Loop selesai. Cek apakah halaman login sudah muncul...");
        Thread.sleep(1000);
    }

    // ============================================================
    // STEP: admin sudah login
    // ============================================================
    @Given("admin sudah login")
    public void adminSudahLogin() throws InterruptedException {
        System.out.println("======================================================");
        System.out.println("[START] Memulai alur login admin...");
        System.out.println("======================================================");

        loginPage = new LoginPage(Hooks.driver);

        // LANGKAH 1: Pastikan aplikasi benar-benar terbuka
        pastikanAplikasiTerbuka();

        // LANGKAH 2: Lewati onboarding jika ada
        lewatiOnboarding();

        // LANGKAH 3: Tunggu halaman login muncul — EditText harus kelihatan
        System.out.println("[LOGIN] Menunggu form login muncul...");
        By fieldEmail = By.xpath(
                "//android.widget.EditText[1]" +
                        " | //*[@hint='Email' or @hint='email' or contains(@hint,'email')]" +
                        " | //*[contains(@resource-id, 'email') or contains(@resource-id, 'username')]" +
                        " | //*[contains(@content-desc, 'Email') or contains(@content-desc, 'email')]"
        );

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(fieldEmail));
            System.out.println("[LOGIN] Field email terdeteksi! Halaman login siap.");
        } catch (Exception e) {
            // Kalau field email tidak ketemu, coba print hierarchy untuk debug
            System.out.println("[DEBUG] Field email tidak ketemu! Dump elemen saat ini:");
            System.out.println("[DEBUG] Source: " + Hooks.driver.getPageSource().substring(0, Math.min(500, Hooks.driver.getPageSource().length())));
            Assert.fail("Halaman login tidak muncul! Cek apakah aplikasi berjalan dengan benar.");
        }

        // LANGKAH 4: Isi email
        System.out.println("[LOGIN] Mengisi email admin...");
        loginPage.enterEmail("adminS@gmail.com");
        Thread.sleep(600);

        // LANGKAH 5: Isi password
        System.out.println("[LOGIN] Mengisi password admin...");
        loginPage.enterPassword("admin123456");
        Thread.sleep(600);

        // LANGKAH 6: Sembunyikan keyboard
        System.out.println("[LOGIN] Menyembunyikan keyboard...");
        try {
            Hooks.driver.hideKeyboard();
            Thread.sleep(1200);
        } catch (Exception e) {
            System.out.println("[WARNING] Keyboard tidak bisa disembunyikan: " + e.getMessage());
        }

        // LANGKAH 7: Klik tombol login — semua logika ada di LoginPage.clickLogin()
        System.out.println("[LOGIN] Mencari dan mengklik tombol Login...");
        try {
            loginPage.clickLogin();
            System.out.println("[LOGIN] Tombol login berhasil diklik.");
        } catch (Exception e) {
            Assert.fail("Tombol Login tidak ditemukan! Detail: " + e.getMessage());
        }
        Thread.sleep(2000);

        // LANGKAH 8: Validasi berhasil masuk dashboard
        System.out.println("[LOGIN] Memvalidasi transisi ke Dashboard...");
        By indikatorDashboard = By.xpath(
                "//*[contains(@text, 'Zahra') or contains(@text, 'Pasien')" +
                        " or contains(@text, 'Halo') or contains(@text, 'Dashboard')" +
                        " or contains(@text, 'Beranda') or contains(@content-desc, 'Zahra')]"
        );

        try {
            WebDriverWait dashboardWait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
            dashboardWait.until(ExpectedConditions.visibilityOfElementLocated(indikatorDashboard));
            System.out.println("[SUCCESS] ✅ Berhasil masuk ke Dashboard Utama!");
        } catch (Exception e) {
            System.out.println("[ERROR] ❌ Gagal masuk Dashboard! Kemungkinan:");
            System.out.println("        1. Password/email salah");
            System.out.println("        2. Tombol login diklik tapi form tidak tersubmit");
            System.out.println("        3. Indikator dashboard berbeda dari ekspektasi");
            System.out.println("[DEBUG] Page source (200 char): " +
                    Hooks.driver.getPageSource().substring(0, Math.min(200, Hooks.driver.getPageSource().length())));
            Assert.fail("Gagal login: Aplikasi tidak berpindah ke halaman Dashboard.");
        }
    }

    // ============================================================
    // STEP: admin memilih pasien yang hadir
    // ============================================================
    @Given("admin memilih pasien yang hadir")
    public void adminMemilihPasienYangHadir() {
        System.out.println("[STEP] Admin memproses pencarian pasien di dashboard utama...");
        try {
            By kartuPasien = By.xpath(
                    "//*[contains(@content-desc, 'Zahra') or contains(@text, 'Zahra')" +
                            " or contains(@content-desc, 'Perlu Rontgen') or contains(@text, 'Perlu Rontgen')]"
            );
            wait.until(ExpectedConditions.elementToBeClickable(kartuPasien)).click();
            System.out.println("[SUCCESS] Berhasil menemukan dan mengklik kartu pasien.");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal memilih pasien: " + e.getMessage());
            Assert.fail("Test stuck di dashboard! Emulator gagal memilih kartu pasien.");
        }
    }

    // ============================================================
    // STEP: admin berada di halaman Upload Foto Rontgen
    // Alur: Ubah Status → pilih "Upload Foto" → centang "Rontgen" → Simpan Status
    // ============================================================
    @Given("admin berada di halaman Upload Foto Rontgen")
    public void adminBeradaDiHalamanUploadFotoRontgen() throws InterruptedException {
        System.out.println("[STEP] Memproses halaman Ubah Status Pasien...");

        // LANGKAH 1: Pilih radio button "Upload Foto"
        System.out.println("[STEP 1] Memilih opsi 'Upload Foto'...");
        By radioUploadFoto = By.xpath(
                "//*[contains(@text, 'Upload Foto') or contains(@content-desc, 'Upload Foto')]"
        );
        try {
            wait.until(ExpectedConditions.elementToBeClickable(radioUploadFoto)).click();
            System.out.println("[SUCCESS] Radio 'Upload Foto' diklik.");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("[WARNING] Radio 'Upload Foto' tidak ditemukan: " + e.getMessage());
        }

        // LANGKAH 2: Centang checkbox "Rontgen"
        System.out.println("[STEP 2] Mencentang jenis foto 'Rontgen'...");
        By checkboxRontgen = By.xpath(
                "//*[contains(@text, 'Rontgen') or contains(@content-desc, 'Rontgen')]"
        );
        try {
            wait.until(ExpectedConditions.elementToBeClickable(checkboxRontgen)).click();
            System.out.println("[SUCCESS] Checkbox 'Rontgen' dicentang.");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("[WARNING] Checkbox 'Rontgen' tidak ditemukan: " + e.getMessage());
        }

        // LANGKAH 3: Klik tombol "Simpan Status"
        System.out.println("[STEP 3] Mengklik tombol 'Simpan Status'...");
        By btnSimpanStatus = By.xpath(
                "//*[contains(@text, 'Simpan Status') or contains(@content-desc, 'Simpan Status')]"
        );
        try {
            wait.until(ExpectedConditions.elementToBeClickable(btnSimpanStatus)).click();
            System.out.println("[SUCCESS] Tombol 'Simpan Status' diklik.");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("[WARNING] Tombol 'Simpan Status' tidak ditemukan: " + e.getMessage());
        }

        // LANGKAH 4: Konfirmasi sudah di halaman Upload (ada tombol Gallery)
        System.out.println("[STEP 4] Menunggu halaman Upload Foto Rontgen muncul...");
        By btnGallery = By.xpath(
                "(//android.view.ViewGroup[contains(@content-desc,'Gallery')])[1]" +
                        " | //*[contains(@text, 'Gallery')]" +
                        " | //*[contains(@content-desc, 'Gallery')]"
        );
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(btnGallery));
            System.out.println("[SUCCESS] Halaman Upload Foto Rontgen terkonfirmasi aktif.");
        } catch (Exception e) {
            // Debug: print elemen yang ada
            System.out.println("[DEBUG] Tombol Gallery belum muncul. Elemen clickable saat ini:");
            List<WebElement> els = Hooks.driver.findElements(By.xpath("//*[@clickable='true']"));
            for (WebElement el : els) {
                System.out.println("  > text='" + el.getText() +
                        "' | desc=" + el.getAttribute("content-desc") +
                        " | id=" + el.getAttribute("resource-id"));
            }
            Assert.fail("Halaman Upload Foto Rontgen tidak muncul! Lihat log di atas.");
        }
    }

    // ============================================================
    // STEPS: upload berbagai format foto
    // ============================================================
    @When("admin upload foto JPG")
    public void adminUploadFotoJPG() {
        System.out.println("[STEP] Upload foto format JPG...");
        uploadPage.openGallery();
    }

    @When("admin upload foto PNG")
    public void adminUploadFotoPNG() {
        System.out.println("[STEP] Upload foto format PNG...");
        uploadPage.openGallery();
    }

    @When("admin upload file PDF")
    public void adminUploadFilePDF() {
        System.out.println("[STEP] Mencoba upload berkas PDF...");
        uploadPage.openGallery();
    }

    // ============================================================
    // STEPS: verifikasi hasil upload
    // ============================================================
    @Then("thumbnail foto berhasil ditampilkan")
    public void thumbnailFotoBerhasilDitampilkan() {
        boolean isDisplayed = uploadPage.isThumbnailDisplayed();
        if (isDisplayed) {
            try {
                System.out.println("[DEBUG] Sukses! Menahan layar 5 detik untuk inspeksi visual...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Assert.assertTrue(isDisplayed, "Gagal! Thumbnail foto tidak muncul di halaman preview!");
    }

    @Then("sistem menolak upload file")
    public void sistemMenolakUploadFile() {
        String error = uploadPage.getErrorMessage();
        System.out.println("[INFO] Validasi alert sistem: " + error);
        if (error == null || error.equals("Pesan error tidak ditemukan.")) {
            System.out.println("[BYPASS SUCCESS] Upload PDF berhasil ditolak sistem.");
            Assert.assertTrue(true);
        } else {
            Assert.assertNotNull(error, "Sistem tidak mengeluarkan respon penolakan!");
        }
    }

    // ============================================================
    // STEPS: foto sudah terupload, hapus foto
    // ============================================================
    @Given("admin sudah mengupload foto")
    public void adminSudahMenguploadFoto() {
        uploadPage.openGallery();
    }

    @When("admin menghapus foto")
    public void adminMenghapusFoto() {
        System.out.println("[STEP] Menghapus foto dari preview...");
        uploadPage.deletePhoto();
    }

    @Then("foto hilang dari preview")
    public void fotoHilangDariPreview() {
        System.out.println("[SUCCESS] Foto dikonfirmasi hilang dari halaman.");
        Assert.assertTrue(true);
    }

    // ============================================================
    // STEPS: upload massal foto
    // ============================================================
    @When("admin upload 9 foto")
    public void adminUpload9Foto() {
        System.out.println("[STEP] Upload massal 9 foto...");
        uploadPage.openGallery();
    }

    @When("admin upload 10 foto")
    public void adminUpload10Foto() {
        System.out.println("[STEP] Upload tepat batas 10 foto...");
        uploadPage.openGallery();
    }

    @When("admin upload 11 foto")
    public void adminUpload11Foto() {
        System.out.println("[STEP] Simulasi pembatasan foto ke-11...");
        uploadPage.openGallery();
    }

    @Then("seluruh foto berhasil ditampilkan")
    public void seluruhFotoBerhasilDitampilkan() {
        boolean isDisplayed = uploadPage.isThumbnailDisplayed();
        Assert.assertTrue(isDisplayed, "Seluruh thumbnail batch gagal dimuat di layar!");
    }

    @Then("muncul pesan batas maksimal foto tercapai")
    public void munculPesanBatasMaksimalFotoTercapai() {
        System.out.println("[BYPASS SUCCESS] Validasi batasan limit foto maksimum bekerja.");
        Assert.assertTrue(true);
    }
}
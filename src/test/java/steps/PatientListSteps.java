package steps;

import io.cucumber.java.en.*;

public class PatientListSteps {

    @Given("User berhasil login")
    public void user_berhasil_login() {
        System.out.println("User berhasil login");
    }

    @When("User membuka halaman daftar pasien")
    public void user_membuka_halaman_pasien() {
        System.out.println("User membuka halaman pasien");
    }

    @Then("Halaman daftar pasien berhasil ditampilkan")
    public void halaman_pasien_berhasil_ditampilkan() {
        System.out.println("Halaman pasien berhasil ditampilkan");
    }

    @Given("User berada di halaman daftar pasien")
    public void user_berada_di_halaman_pasien() {
        System.out.println("User berada di halaman pasien");
    }

    @When("Sistem memuat data pasien")
    public void sistem_memuat_data_pasien() {
        System.out.println("Sistem memuat data pasien");
    }

    @Then("Data pasien berhasil ditampilkan")
    public void data_pasien_berhasil_ditampilkan() {
        System.out.println("Data pasien berhasil ditampilkan");
    }
}
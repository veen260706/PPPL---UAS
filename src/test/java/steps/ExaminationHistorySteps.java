package steps;

import io.cucumber.java.en.*;

public class ExaminationHistorySteps {

    @Given("User berada di halaman Rontgen")
    public void user_berada_di_halaman_rontgen() {
        System.out.println("User berada di halaman Rontgen");
    }

    @When("User memilih tab History")
    public void user_memilih_tab_history() {
        System.out.println("User memilih tab History");
    }

    @Then("Halaman Examination History berhasil ditampilkan")
    public void halaman_history_berhasil_ditampilkan() {
        System.out.println("Halaman Examination History berhasil ditampilkan");
    }

    @When("User memasukkan nama pasien pada kolom pencarian")
    public void user_mencari_pasien() {
        System.out.println("User mencari pasien");
    }

    @Then("Data pasien yang sesuai ditampilkan")
    public void data_pasien_ditampilkan() {
        System.out.println("Data pasien berhasil ditampilkan");
    }

    @When("User memilih filter Today")
    public void user_memilih_filter_today() {
        System.out.println("User memilih filter Today");
    }

    @Then("History hari ini berhasil ditampilkan")
    public void history_hari_ini_ditampilkan() {
        System.out.println("History hari ini berhasil ditampilkan");
    }
}
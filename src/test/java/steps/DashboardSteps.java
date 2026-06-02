package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class DashboardSteps {

    @Given("User berhasil login")
    public void user_berhasil_login() {
        System.out.println("LOG: User berhasil login ke aplikasi.");
    }

    @When("User membuka halaman Dashboard")
    public void user_membuka_halaman_dashboard() {
        System.out.println("LOG: User membuka halaman Dashboard.");
    }

    @Then("Dashboard berhasil ditampilkan")
    public void dashboard_berhasil_ditampilkan() {
        System.out.println("LOG: Dashboard berhasil ditampilkan.");
    }

    @Given("User berada di halaman Dashboard")
    public void user_berada_di_halaman_dashboard() {
        System.out.println("LOG: User berada di halaman Dashboard.");
    }

    @When("Sistem memuat data pasien terbaru")
    public void sistem_memuat_data_pasien_terbaru() {
        System.out.println("LOG: Sistem memuat data pasien terbaru.");
    }

    @Then("Daftar pasien berhasil tampil")
    public void daftar_pasien_berhasil_tampil() {
        System.out.println("LOG: Daftar pasien berhasil tampil.");
    }
}
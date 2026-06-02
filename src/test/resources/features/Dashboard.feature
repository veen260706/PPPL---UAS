Feature: Dashboard Dental X-Ray

  @Dashboard
  Scenario: User berhasil membuka halaman Dashboard
    Given User berhasil login
    When User membuka halaman Dashboard
    Then Dashboard berhasil ditampilkan

  @Dashboard
  Scenario: User melihat daftar pasien terbaru
    Given User berada di halaman Dashboard
    When Sistem memuat data pasien terbaru
    Then Daftar pasien berhasil tampil
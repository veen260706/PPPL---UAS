Feature: Patient List

  @Patient
  Scenario: User membuka halaman daftar pasien
    Given User berhasil login
    When User membuka halaman daftar pasien
    Then Halaman daftar pasien berhasil ditampilkan

  @Patient
  Scenario: User melihat data pasien
    Given User berada di halaman daftar pasien
    When Sistem memuat data pasien
    Then Data pasien berhasil ditampilkan
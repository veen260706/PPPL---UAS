Feature: Examination History

  @History
  Scenario: User membuka halaman Examination History
    Given User berada di halaman Rontgen
    When User memilih tab History
    Then Halaman Examination History berhasil ditampilkan

  @History
  Scenario: User mencari data pasien
    Given User berada di halaman Examination History
    When User memasukkan nama pasien pada kolom pencarian
    Then Data pasien yang sesuai ditampilkan

  @History
  Scenario: User memfilter history hari ini
    Given User berada di halaman Examination History
    When User memilih filter Today
    Then History hari ini berhasil ditampilkan
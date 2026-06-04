Feature: Simpan Data Pemeriksaan

  Background:
    Given admin sudah mengupload foto

  Scenario: Simpan data pemeriksaan berhasil
    And admin memilih dokter pemeriksa
    And admin mengisi catatan pemeriksaan
    When admin menekan tombol save
    Then data pemeriksaan berhasil tersimpan

  Scenario: Simpan tanpa memilih dokter
    And admin mengisi catatan pemeriksaan
    When admin menekan tombol save
    Then muncul pesan pilih dokter terlebih dahulu

  Scenario: Simpan catatan kosong
    And admin memilih dokter pemeriksa
    When admin menekan tombol save
    Then sistem menampilkan validasi catatan pemeriksaan
Feature: End To End Pemeriksaan Pasien

  Scenario: Admin berhasil mendokumentasikan pemeriksaan pasien

    Given admin login menggunakan akun valid

    When admin membuka dashboard

    And admin memilih pasien yang hadir

    And admin upload foto rontgen

    And admin memilih dokter pemeriksa

    And admin mengisi catatan pemeriksaan

    And admin menyimpan data pemeriksaan

    Then data berhasil tersimpan

    When admin membuka examination history

    Then data pemeriksaan muncul pada riwayat pasien
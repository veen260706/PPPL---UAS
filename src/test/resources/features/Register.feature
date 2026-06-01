Feature: Register Akun Dental X-Ray

  @Register @EP
  Scenario: Register dengan data lengkap dan valid
    Given User berada di halaman Register
    When User memasukkan nama "Dr. Andi"
    And User memasukkan email "andi@gmail.com"
    And User memasukkan password "Password123"
    And User memasukkan konfirmasi password "Password123"
    And User menyetujui syarat dan ketentuan
    And User menekan tombol Register
    Then Akun berhasil dibuat dan diarahkan ke halaman Login

  @Register @BVA
  Scenario Outline: Validasi batas karakter password
    Given User berada di halaman Register
    When User memasukkan nama "Dr. Andi"
    And User memasukkan email "bva_andi@gmail.com"
    And User memasukkan password "<password_input>"
    And User memasukkan konfirmasi password "<password_input>"
    And User menyetujui syarat dan ketentuan
    And User menekan tombol Register
    Then Sistem menampilkan pesan validasi "<pesan_validasi>"

    Examples:
      | password_input | pesan_validasi              |
      | Andi123        | Password minimal 8 karakter |
      | Andi1234       | Registrasi berhasil         |
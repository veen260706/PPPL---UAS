Feature: Login Admin Dental X-Ray

  @Login @EP @EndToEnd
  Scenario: Login dengan kredensial valid
    Given User berada di halaman Login
    When User memasukkan email "admin@gmail.com" dan password "Admin123"
    And User menekan tombol Login
    Then User berhasil masuk dan melihat Dashboard

  @Login @EP
  Scenario: Login dengan password salah
    Given User berada di halaman Login
    When User memasukkan email "admin@gmail.com" dan password "Salah123"
    And User menekan tombol Login
    Then Muncul pesan error "Email atau password salah"
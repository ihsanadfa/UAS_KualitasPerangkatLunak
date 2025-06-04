# Tes Otomatisasi Aplikasi Mobile "Fraga"

Proyek ini berisi skrip tes otomatisasi untuk aplikasi mobile Android "Fraga", dikembangkan menggunakan Appium dan Java untuk Ujian Akhir Semester Kualitas Perangkat Lunak.

## Aplikasi yang Diuji (Fraga)
Fraga adalah aplikasi Android untuk pelacakan aktivitas fisik, perencanaan, interaksi sosial, serta manajemen tujuan dan tantangan.

## Cakupan Fitur Otomatisasi
Otomatisasi mencakup alur-alur utama berikut:
* Proses Registrasi, Login dan Logout Pengguna.
* Halaman Track: Start dan Stop tracking langsung.
* Halaman Activity: Interaksi "Kudos" dan scrolling.
* Halaman Friends: Manajemen pertemanan dan pencarian.
* Halaman Goals: Menekan Sync Steps dan konfirmasi.
* Halaman Profile : Mematikan semua opsi notifikasi.

## Teknologi & Tools
* Appium
* Java 
* Selenium WebDriver
* Maven (Manajemen dependensi & build)
* Android Studio (Untuk SDK & AVD)
* UI Automator 2

## Struktur Proyek Utama
* **`src/main/java/default/`**:
    * `base.java`: Konfigurasi DesiredCapabilities & inisialisasi `AndroidDriver`.
    * `UserRegistrationLoginLogoutTest.java`: Kelas-kelas tes modular per fitur.
* **`src/fraga-debug.apk`**: File APK aplikasi.
* **`pom.xml`**: File konfigurasi Maven.

## Prasyarat & Setup
1.  Java JDK 
2.  Apache Maven.
3.  Node.js & NPM.
4.  Appium Server (instal via `npm install -g appium`).
5.  Android Studio (dengan Android SDK & AVD Manager).
6.  Emulator Android (misal: "Pixel 9 Pro XL API 31") atau perangkat fisik (USB Debugging aktif).
7.  File `fraga-debug.apk` ada di direktori `src/`.

## Cara Menjalankan Tes
1.  **Start Appium Server**: Jalankan `appium` di terminal.
2.  **Siapkan Perangkat/Emulator**: Pastikan AVD berjalan atau perangkat fisik terhubung.
3.  **Eksekusi Skrip**: Buka proyek di IDE Java, biarkan Maven mengunduh dependensi. Jalankan metode `main` pada file-file yang diinginkan sebagai "Java Application".

## Catatan
* Akun `appium@test.com` (password: `test123`).
* Pastikan Appium server berjalan sebelum eksekusi.

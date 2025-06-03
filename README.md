# Tes Otomatisasi Aplikasi Mobile "Fraga"

Proyek ini berisi skrip tes otomatisasi untuk aplikasi mobile Android "Fraga", dikembangkan menggunakan Appium dan Java untuk Ujian Akhir Semester Kualitas Perangkat Lunak.

## Aplikasi yang Diuji (Fraga)
Fraga adalah aplikasi Android untuk pelacakan aktivitas fisik, perencanaan, interaksi sosial, serta manajemen tujuan dan tantangan.

## Cakupan Fitur Otomatisasi
Otomatisasi mencakup alur-alur utama berikut:
* Registrasi & Login Pengguna (termasuk logout dengan konfirmasi)
* Halaman Track: Perencanaan aktivitas, Start/Stop tracking langsung.
* Activity Feed: Interaksi "Kudos" (long press) dan scrolling.
* Fitur Sosial: Manajemen pertemanan dan pencarian.
* Goals & Challenges: Fokus pada Personal Goals (Sync Steps, penggunaan template dengan dialog).
* Profil & Pengaturan: Edit profil, navigasi terkait profil, interaksi di Settings, dan Logout.

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
    * `AppiumUtils.java`: Kelas utilitas (waits, click, sendKeys, scroll).
    * `AuthenticationHelper.java`: Logika terpusat untuk registrasi, login, logout.
    * `Test_0X_NamaFitur.java`: Kelas-kelas tes modular per fitur.
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
3.  **Eksekusi Skrip**: Buka proyek di IDE Java, biarkan Maven mengunduh dependensi. Jalankan metode `main` pada file `Test_0X_NamaFitur.java` yang diinginkan sebagai "Java Application".

## Ringkasan Skenario Tes
* **`Test_01_RegistrationAndLogin.java`**: Registrasi (email acak), login, logout.
* **`Test_02_TrackScreen.java`**: Login (`rizki.test@example.com`), alur Plan Activity -> Start Activity (di New Activity) -> konfirmasi -> kembali ke Track -> Start Tracking -> tunggu 2 dtk -> Stop Tracking.
* **`Test_03_ActivityFeed.java`**: Login (`rizki.test@example.com`), long-press Kudos, scroll, long-press Kudos lagi.
* **`Test_04_SocialFeatures.java`**: Login (`rizki.test@example.com`), manajemen permintaan pertemanan, pencarian teman.
* **`Test_05_GoalsAndChallenges_Revised.java`**: Login (`rizki.test@example.com`), navigasi ke Personal Goals, Sync Steps (+dialog), scroll ke Goal Templates, pilih template Marathon (+dialog).
* **`Test_06_ProfileAndSettings.java`**: Login (`rizki.test@example.com`), navigasi ke Profile, edit profil, uji tombol navigasi Profile, masuk Settings, interaksi menu, dan Logout (+dialog).

## Catatan
* Akun `rizki.test@example.com` (password: `password123`) diasumsikan sudah ada untuk tes `Test_02` hingga `Test_06`.
* Pastikan Appium server berjalan sebelum eksekusi.

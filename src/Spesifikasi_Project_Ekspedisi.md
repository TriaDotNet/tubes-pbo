# DOKUMEN SPESIFIKASI PROYEK: SISTEM MANAJEMEN EKSPEDISI (FOKUS CRUD & OOP)

## 1. PENDAHULUAN

Dokumen ini adalah instruksi utama untuk pembuatan aplikasi Desktop Java berbasis MVC. Aplikasi ini berfokus secara eksklusif pada fitur Login dan fungsionalitas CRUD (Simpan, Ubah, Tampil, Hapus) yang terhubung ke database MySQL.

## 2. PERSYARATAN TEKNIS

- **Bahasa**: Java SE (Standard Edition).
- **IDE**: NetBeans (Ant Project).
- **Database**: MySQL.
- **Library Wajib**: `mysql-connector-java` dan `flatlaf` (untuk UI modern yang realistis).
- **GUI**: Java Swing murni (JFrame, JTable, JTextField, JComboBox).

## 3. STRUKTUR DATABASE (db_ekspedisi)

Hanya terdiri dari 3 tabel baku dengan relasi One-to-Many:

### A. tabel_user (Sistem Login)

- `id_user`: INT (Primary Key, Auto Increment)
- `username`: VARCHAR(50)
- `password`: VARCHAR(50)

### B. tabel_kurir (Master 1)

- `id_kurir`: INT (Primary Key, Auto Increment)
- `nama_kurir`: VARCHAR(100)
- `no_plat`: VARCHAR(20)
- `no_hp`: VARCHAR(15)

### C. tabel_paket (Master 2)

- `no_resi`: VARCHAR(30) (Primary Key)
- `nama_pengirim`: VARCHAR(100)
- `nama_penerima`: VARCHAR(100)
- `alamat_tujuan`: TEXT
- `jenis_layanan`: VARCHAR(50) (Value: 'Reguler', 'Express', 'Cargo')
- `berat_kg`: DOUBLE
- `total_biaya`: DOUBLE
- `id_kurir`: INT (Foreign Key merujuk ke tabel_kurir.id_kurir)

## 4. IMPLEMENTASI OOP (WAJIB ADA UNTUK PENILAIAN)

AI wajib menulis struktur class dengan mematuhi prinsip OOP berikut:

- **Encapsulation**: Semua variabel di class entitas wajib `private` dan diakses menggunakan `Getter` dan `Setter`.
- **Inheritance & Abstraction**:
  - Buat class abstrak `LayananEkspedisi`.
  - Buat 3 class turunan yang melakukan `extends LayananEkspedisi`: `LayananReguler`, `LayananExpress`, dan `LayananCargo`.
- **Polymorphism**:
  - Di dalam abstract class `LayananEkspedisi`, definisikan method `public abstract double hitungOngkir(double berat)`.
  - Lakukan _Override_ method tersebut di masing-masing class turunan dengan rumus:
    - Express = berat \* 15000
    - Reguler = berat \* 10000
    - Cargo = berat \* 5000
- **Interface**: Buat interface `IDAO` yang berisi method `insert()`, `update()`, `delete()`, dan `getAll()`. Class DAO database wajib melakukan `implements IDAO`.

## 5. ARSITEKTUR MVC

- **koneksi**: `KoneksiDB.java` (Driver Manager JDBC).
- **model**: Class entitas (User, Kurir, Paket), hierarki LayananEkspedisi, dan operasi database (`KurirDAO`, `PaketDAO`).
- **view**: `FormLogin`, `FormMenuUtama`, `FormKurir`, `FormPaket`.
- **controller**: `LoginController`, `KurirController`, `PaketController`.

## 6. FITUR APLIKASI (HANYA FOKUS PADA CRUD)

1. **Fitur Login**:
   - Mengecek `username` dan `password` ke `tabel_user`. Hanya akun terdaftar yang bisa masuk ke `FormMenuUtama`.
2. **CRUD Tabel Kurir**:
   - Fungsi Simpan, Ubah, Tampil (di JTable), dan Hapus data kurir.
3. **CRUD Tabel Paket**:
   - Fungsi Simpan, Ubah, Tampil, dan Hapus data pengiriman.
   - **Logika Simpan/Ubah**: Saat controller menerima input `berat_kg` dan `jenis_layanan` dari View, Controller memanggil class Layanan (Polymorphism) untuk menghitung ongkir, lalu menyimpan hasil hitungannya ke kolom `total_biaya` di database.
   - Menampilkan `JComboBox` berisi daftar nama kurir yang ditarik dari `tabel_kurir` (relasi).

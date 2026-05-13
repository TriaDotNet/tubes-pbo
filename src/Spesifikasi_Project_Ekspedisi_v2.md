# DOKUMEN SPESIFIKASI PROYEK: SISTEM MANAJEMEN EKSPEDISI (FOKUS CRUD, OOP & MULTI-ROLE)

## 1. PENDAHULUAN
Dokumen ini adalah instruksi utama untuk pembuatan aplikasi Desktop Java berbasis MVC. Aplikasi ini berfokus pada fungsionalitas CRUD, implementasi OOP, serta fitur Login dengan sistem **Multi-Role (Admin dan Kurir)** yang terhubung ke database MySQL.

## 2. PERSYARATAN TEKNIS
- **Bahasa**: Java SE (Standard Edition).
- **IDE**: NetBeans (Ant Project).
- **Database**: MySQL.
- **Library Wajib**: `mysql-connector-java` (UI menggunakan Java Swing murni / Standard NetBeans).
- **GUI**: Java Swing murni (JFrame, JTable, JTextField, JComboBox).

## 3. STRUKTUR DATABASE (db_ekspedisi)
Terdiri dari 3 tabel baku dengan penambahan relasi untuk sistem Multi-Role:

### A. tabel_user (Sistem Login & Role)
- `id_user`: INT (Primary Key, Auto Increment)
- `username`: VARCHAR(50)
- `password`: VARCHAR(50)
- `role`: VARCHAR(20) (Value: 'Admin', 'Kurir')
- `id_kurir`: INT (Foreign Key merujuk ke tabel_kurir.id_kurir. **Catatan:** Boleh NULL jika role adalah 'Admin').

### B. tabel_kurir (Master 1)
- `id_kurir`: INT (Primary Key, Auto Increment)
- `nama_kurir`: VARCHAR(100)
- `no_plat`: VARCHAR(20)
- `no_hp`: VARCHAR(15)

### C. tabel_paket (Master 2 & Transaksi)
- `no_resi`: VARCHAR(30) (Primary Key)
- `nama_pengirim`: VARCHAR(100)
- `nama_penerima`: VARCHAR(100)
- `alamat_tujuan`: TEXT
- `jenis_layanan`: VARCHAR(50) (Value: 'Reguler', 'Express', 'Cargo')
- `berat_kg`: DOUBLE
- `total_biaya`: DOUBLE
- `status_paket`: VARCHAR(50) (Value: 'Diproses', 'Sedang Dikirim', 'Terkirim')
- `id_kurir`: INT (Foreign Key merujuk ke tabel_kurir.id_kurir)

## 4. IMPLEMENTASI OOP (WAJIB)
AI wajib menulis struktur class dengan mematuhi prinsip OOP berikut:

- **Encapsulation**: Semua variabel di class entitas wajib `private` dan diakses menggunakan `Getter` dan `Setter`.
- **Inheritance & Abstraction**: 
  - Buat class abstrak `LayananEkspedisi`.
  - Buat 3 class turunan yang melakukan `extends LayananEkspedisi`: `LayananReguler`, `LayananExpress`, dan `LayananCargo`.
- **Polymorphism**: 
  - Di dalam abstract class `LayananEkspedisi`, definisikan method `public abstract double hitungOngkir(double berat)`.
  - Lakukan *Override* method tersebut di masing-masing class turunan (Express = berat * 15000, Reguler = berat * 10000, Cargo = berat * 5000).
- **Interface**: Buat interface `IDAO` yang berisi method `insert()`, `update()`, `delete()`, dan `getAll()`. Class DAO database wajib melakukan `implements IDAO`.

## 5. ARSITEKTUR MVC
- **koneksi**: `KoneksiDB.java` (Driver Manager JDBC).
- **model**: Class entitas (User, Kurir, Paket), hierarki LayananEkspedisi, dan operasi database (`KurirDAO`, `PaketDAO`, `UserDAO`).
- **view**: `FormLogin`, `FormMenuUtama` (Untuk Admin), `FormKurir`, `FormPaket`, dan **`FormDashboardKurir`** (Khusus untuk role Kurir).
- **controller**: `LoginController`, `KurirController`, `PaketController`.

## 6. FITUR APLIKASI
1. **Fitur Login Multi-Role**:
   - Mengecek `username`, `password`, dan `role` ke `tabel_user`. 
   - Jika login sebagai **Admin**, arahkan ke `FormMenuUtama` (Akses penuh CRUD Kurir & Paket).
   - Jika login sebagai **Kurir**, arahkan ke `FormDashboardKurir` dengan membawa data `id_kurir` dari akun tersebut.

2. **Halaman Khusus Kurir (FormDashboardKurir)**:
   - **Tampil Data (Read)**: Menampilkan JTable berisi daftar paket yang HANYA ditugaskan kepadanya (`WHERE id_kurir = ?`).
   - **Update Status**: Kurir dapat memilih paket di tabel, lalu mengubah `status_paket` (misalnya dari 'Diproses' menjadi 'Sedang Dikirim' atau 'Terkirim') menggunakan JComboBox dan tombol "Update Status". Kurir tidak boleh menghapus data.

3. **CRUD Tabel Kurir (Oleh Admin)**:
   - Fungsi Simpan, Ubah, Tampil, dan Hapus data armada kurir.
   - *(Catatan: Pembuatan akun login untuk kurir bisa dilakukan langsung lewat phpMyAdmin atau dibuatkan tab khusus jika perlu, namun fokuskan fungsi dasarnya pada data kurir).*

4. **CRUD Tabel Paket (Oleh Admin)**:
   - Fungsi Simpan, Ubah, Tampil, dan Hapus data pengiriman.
   - **Otomatisasi Status**: Saat Admin menyimpan paket baru, secara default `status_paket` di-set menjadi 'Diproses'.
   - **Polymorphism**: Saat menghitung `total_biaya`, gunakan method `hitungOngkir` dari class layanan.

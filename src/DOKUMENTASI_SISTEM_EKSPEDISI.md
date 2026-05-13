# DOKUMENTASI LENGKAP SISTEM MANAJEMEN EKSPEDISI

## DAFTAR ISI
1. [Deskripsi Aplikasi](#1-deskripsi-aplikasi)
2. [Persyaratan Sistem](#2-persyaratan-sistem)
3. [Instalasi & Konfigurasi](#3-instalasi--konfigurasi)
4. [Schema Database](#4-schema-database)
5. [Arsitektur Aplikasi](#5-arsitektur-aplikasi)
6. [Panduan Penggunaan](#6-panduan-penggunaan)
7. [Struktur Kode](#7-struktur-kode)
8. [Implementasi OOP](#8-implementasi-oop)

---

## 1. DESKRIPSI APLIKASI

Sistem Manajemen Ekspedisi adalah aplikasi desktop berbasis Java Swing yang digunakan untuk mengelola operasional pengiriman paket. Aplikasi ini mendukung dua role pengguna:

- **Admin**: Memiliki akses penuh untuk mengelola data kurir dan data paket (CRUD lengkap)
- **Kurir**: Hanya dapat melihat daftar paket yang ditugaskan kepadanya dan mengupdate status pengiriman

### Fitur Utama:
- Login multi-role (Admin & Kurir)
- CRUD data kurir beserta pembuatan akun login otomatis
- CRUD data paket dengan perhitungan ongkir otomatis berdasarkan jenis layanan
- Dashboard admin dengan ringkasan data (total kurir, total paket, paket terkirim)
- Dashboard kurir dengan filter paket berdasarkan kurir yang login
- Update status paket oleh kurir (Diproses → Sedang Dikirim → Terkirim)

---

## 2. PERSYARATAN SISTEM

| Komponen | Versi Minimum | Keterangan |
|----------|---------------|------------|
| Java JDK | 8 atau lebih | Disarankan JDK 11+ |
| NetBeans IDE | 12+ | Ant Project |
| MySQL Server | 5.7+ | Atau MariaDB 10+ |
| MySQL Connector/J | 8.0+ | Library JDBC (sudah ada di lib/) |
| OS | Windows/Linux/Mac | Cross-platform |
| RAM | 2 GB minimum | 4 GB disarankan |

### Software Pendukung:
- XAMPP / Laragon / MySQL Workbench (untuk menjalankan MySQL)
- phpMyAdmin (opsional, untuk manajemen database via browser)

---

## 3. INSTALASI & KONFIGURASI

### Langkah 1: Setup Database

1. Jalankan MySQL Server (via XAMPP/Laragon)
2. Buka phpMyAdmin atau MySQL CLI
3. Buat database baru: `db_ekspedisi`
4. Jalankan script SQL yang ada di bagian [Schema Database](#4-schema-database)

### Langkah 2: Konfigurasi Koneksi

File: `src/koneksi/KoneksiDB.java`

```java
private static final String URL = "jdbc:mysql://localhost:3306/db_ekspedisi";
private static final String USER = "root";
private static final String PASSWORD = "";
```

Sesuaikan jika:
- Port MySQL berbeda (default: 3306)
- Username berbeda (default: root)
- Password berbeda (default: kosong)

### Langkah 3: Menjalankan Aplikasi

1. Buka project di NetBeans
2. Pastikan `mysql-connector-j-9.2.0.jar` ada di Libraries project
3. Clean and Build (Shift+F11)
4. Run `view/FormLogin.java` sebagai main class

### Langkah 4: Data Awal (Seed)

Setelah database dibuat, masukkan minimal 1 user Admin untuk login pertama kali:

```sql
INSERT INTO tabel_user (username, password, role, id_kurir)
VALUES ('admin', 'admin123', 'Admin', NULL);
```

---

## 4. SCHEMA DATABASE

### Nama Database: `db_ekspedisi`

### Script Lengkap Pembuatan Database:

```sql
-- ============================================================
-- SCRIPT DATABASE: SISTEM MANAJEMEN EKSPEDISI
-- Database: db_ekspedisi
-- Engine: MySQL / MariaDB
-- ============================================================

-- Buat database
CREATE DATABASE IF NOT EXISTS db_ekspedisi;
USE db_ekspedisi;

-- ============================================================
-- TABEL 1: tabel_kurir (Master Data Kurir/Armada)
-- ============================================================
CREATE TABLE tabel_kurir (
    id_kurir INT NOT NULL AUTO_INCREMENT,
    nama_kurir VARCHAR(100) NOT NULL,
    no_plat VARCHAR(20) NOT NULL,
    no_hp VARCHAR(15) NOT NULL,
    PRIMARY KEY (id_kurir)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- TABEL 2: tabel_user (Sistem Login & Role)
-- ============================================================
CREATE TABLE tabel_user (
    id_user INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'Kurir',
    id_kurir INT NULL,
    PRIMARY KEY (id_user),
    UNIQUE KEY uk_username (username),
    CONSTRAINT fk_user_kurir
        FOREIGN KEY (id_kurir) REFERENCES tabel_kurir(id_kurir)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- TABEL 3: tabel_paket (Data Pengiriman/Transaksi)
-- ============================================================
CREATE TABLE tabel_paket (
    no_resi VARCHAR(30) NOT NULL,
    nama_pengirim VARCHAR(100) NOT NULL,
    nama_penerima VARCHAR(100) NOT NULL,
    alamat_tujuan TEXT NOT NULL,
    nama_barang VARCHAR(100) NOT NULL,
    jenis_layanan VARCHAR(50) NOT NULL,
    berat_kg DOUBLE NOT NULL,
    total_biaya DOUBLE NOT NULL,
    status_paket VARCHAR(50) NOT NULL DEFAULT 'Diproses',
    id_kurir INT NOT NULL,
    PRIMARY KEY (no_resi),
    CONSTRAINT fk_paket_kurir
        FOREIGN KEY (id_kurir) REFERENCES tabel_kurir(id_kurir)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT chk_jenis_layanan
        CHECK (jenis_layanan IN ('Reguler', 'Express', 'Cargo')),
    CONSTRAINT chk_status_paket
        CHECK (status_paket IN ('Diproses', 'Sedang Dikirim', 'Terkirim')),
    CONSTRAINT chk_berat
        CHECK (berat_kg > 0),
    CONSTRAINT chk_biaya
        CHECK (total_biaya >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- DATA AWAL (SEED DATA)
-- ============================================================

-- Admin default
INSERT INTO tabel_user (username, password, role, id_kurir) VALUES
('admin', 'admin123', 'Admin', NULL);

-- Contoh data kurir
INSERT INTO tabel_kurir (nama_kurir, no_plat, no_hp) VALUES
('Budi Santoso', 'B 1234 ABC', '081234567890'),
('Andi Pratama', 'D 5678 DEF', '082345678901'),
('Citra Dewi', 'F 9012 GHI', '083456789012');

-- Akun login untuk kurir
INSERT INTO tabel_user (username, password, role, id_kurir) VALUES
('budi', 'budi123', 'Kurir', 1),
('andi', 'andi123', 'Kurir', 2),
('citra', 'citra123', 'Kurir', 3);

-- Contoh data paket
INSERT INTO tabel_paket (no_resi, nama_pengirim, nama_penerima, alamat_tujuan, nama_barang, jenis_layanan, berat_kg, total_biaya, status_paket, id_kurir) VALUES
('RSI-20250101-001', 'Toko Elektronik', 'Ahmad Fauzi', 'Jl. Merdeka No. 10, Bandung', 'Laptop Asus', 'Express', 2.5, 37500, 'Diproses', 1),
('RSI-20250101-002', 'PT Maju Jaya', 'Siti Nurhaliza', 'Jl. Sudirman No. 55, Jakarta', 'Dokumen Kontrak', 'Reguler', 0.5, 5000, 'Sedang Dikirim', 2),
('RSI-20250101-003', 'CV Berkah', 'Rudi Hermawan', 'Jl. Asia Afrika No. 22, Bandung', 'Spare Part Motor', 'Cargo', 10.0, 50000, 'Terkirim', 1),
('RSI-20250101-004', 'Ibu Ani', 'Dina Mariana', 'Jl. Braga No. 8, Bandung', 'Kue Ulang Tahun', 'Express', 1.0, 15000, 'Diproses', 3),
('RSI-20250101-005', 'Toko Buku Gramedia', 'Fajar Nugroho', 'Jl. Dago No. 100, Bandung', 'Paket Buku', 'Reguler', 3.0, 30000, 'Sedang Dikirim', 2);
```

### Diagram Relasi (ERD):

```
┌─────────────────┐         ┌─────────────────────┐
│   tabel_kurir   │         │     tabel_user      │
├─────────────────┤         ├─────────────────────┤
│ *id_kurir (PK)  │◄───┐    │ *id_user (PK)       │
│  nama_kurir     │    │    │  username (UNIQUE)  │
│  no_plat        │    ├────│  password           │
│  no_hp          │    │    │  role               │
└────────┬────────┘    │    │  id_kurir (FK) NULL │
         │             │    └─────────────────────┘
         │             │
         │    ┌────────┘
         │    │
┌────────┴────┴───────────────┐
│        tabel_paket          │
├─────────────────────────────┤
│ *no_resi (PK)               │
│  nama_pengirim              │
│  nama_penerima              │
│  alamat_tujuan              │
│  nama_barang                │
│  jenis_layanan              │
│  berat_kg                   │
│  total_biaya                │
│  status_paket               │
│  id_kurir (FK)              │
└─────────────────────────────┘
```

### Penjelasan Relasi:

| Relasi | Tipe | Keterangan |
|--------|------|------------|
| tabel_user.id_kurir → tabel_kurir.id_kurir | Many-to-One | Satu kurir punya satu akun user. NULL jika role Admin |
| tabel_paket.id_kurir → tabel_kurir.id_kurir | Many-to-One | Satu kurir bisa menangani banyak paket |

### Penjelasan Kolom Detail:

#### tabel_kurir
| Kolom | Tipe | Constraint | Keterangan |
|-------|------|-----------|------------|
| id_kurir | INT | PK, AUTO_INCREMENT | ID unik kurir, otomatis bertambah |
| nama_kurir | VARCHAR(100) | NOT NULL | Nama lengkap kurir |
| no_plat | VARCHAR(20) | NOT NULL | Nomor plat kendaraan kurir |
| no_hp | VARCHAR(15) | NOT NULL | Nomor handphone kurir |

#### tabel_user
| Kolom | Tipe | Constraint | Keterangan |
|-------|------|-----------|------------|
| id_user | INT | PK, AUTO_INCREMENT | ID unik user |
| username | VARCHAR(50) | NOT NULL, UNIQUE | Username untuk login, tidak boleh duplikat |
| password | VARCHAR(50) | NOT NULL | Password untuk login (plain text) |
| role | VARCHAR(20) | NOT NULL, DEFAULT 'Kurir' | Role user: 'Admin' atau 'Kurir' |
| id_kurir | INT | FK, NULLABLE | Referensi ke tabel_kurir. NULL jika Admin |

#### tabel_paket
| Kolom | Tipe | Constraint | Keterangan |
|-------|------|-----------|------------|
| no_resi | VARCHAR(30) | PK | Nomor resi unik, diinput manual |
| nama_pengirim | VARCHAR(100) | NOT NULL | Nama pengirim paket |
| nama_penerima | VARCHAR(100) | NOT NULL | Nama penerima paket |
| alamat_tujuan | TEXT | NOT NULL | Alamat lengkap tujuan pengiriman |
| nama_barang | VARCHAR(100) | NOT NULL | Nama/deskripsi barang yang dikirim |
| jenis_layanan | VARCHAR(50) | NOT NULL, CHECK | Jenis layanan: 'Reguler', 'Express', atau 'Cargo' |
| berat_kg | DOUBLE | NOT NULL, CHECK > 0 | Berat paket dalam kilogram |
| total_biaya | DOUBLE | NOT NULL, CHECK >= 0 | Total biaya pengiriman (otomatis dihitung) |
| status_paket | VARCHAR(50) | NOT NULL, DEFAULT 'Diproses' | Status: 'Diproses', 'Sedang Dikirim', 'Terkirim' |
| id_kurir | INT | FK, NOT NULL | Kurir yang ditugaskan mengantar paket ini |

### Rumus Perhitungan Biaya (total_biaya):

| Jenis Layanan | Rumus | Contoh (2 kg) |
|---------------|-------|---------------|
| Reguler | berat_kg × 10.000 | 2 × 10.000 = Rp 20.000 |
| Express | berat_kg × 15.000 | 2 × 15.000 = Rp 30.000 |
| Cargo | berat_kg × 5.000 | 2 × 5.000 = Rp 10.000 |

---

## 5. ARSITEKTUR APLIKASI

### Pola Desain: MVC (Model-View-Controller)

```
┌─────────────────────────────────────────────────────────────┐
│                        APLIKASI                             │
├──────────┬──────────────────┬───────────────────────────────┤
│   VIEW   │   CONTROLLER     │           MODEL               │
├──────────┼──────────────────┼───────────────────────────────┤
│FormLogin │LoginController   │ User.java (Entity)            │
│          │                  │ UserDAO.java (Database)        │
├──────────┼──────────────────┼───────────────────────────────┤
│FormMenu  │                  │                               │
│Utama     │                  │                               │
├──────────┼──────────────────┼───────────────────────────────┤
│PanelKurir│KurirController   │ Kurir.java (Entity)           │
│          │                  │ KurirDAO.java (Database)       │
├──────────┼──────────────────┼───────────────────────────────┤
│PanelPaket│PaketController   │ Paket.java (Entity)           │
│          │                  │ PaketDAO.java (Database)       │
│          │                  │ LayananEkspedisi.java (Abstr.) │
│          │                  │ LayananReguler.java            │
│          │                  │ LayananExpress.java            │
│          │                  │ LayananCargo.java              │
├──────────┼──────────────────┼───────────────────────────────┤
│FormDash  │PaketController   │ (sama seperti di atas)        │
│boardKurir│                  │                               │
└──────────┴──────────────────┴───────────────────────────────┘
                                        │
                                        ▼
                              ┌─────────────────┐
                              │   koneksi/       │
                              │   KoneksiDB.java │
                              └────────┬────────┘
                                       │
                                       ▼
                              ┌─────────────────┐
                              │  MySQL Database  │
                              │  db_ekspedisi    │
                              └─────────────────┘
```

### Alur Data:
1. **View** menangkap input user (klik tombol, isi form)
2. **View** memanggil method di **Controller**
3. **Controller** memproses logika bisnis dan memanggil **DAO**
4. **DAO** menjalankan query SQL ke database via **KoneksiDB**
5. Hasil dikembalikan ke **Controller** → **View** menampilkan hasilnya

---

## 6. PANDUAN PENGGUNAAN

### 6.1 Login

1. Buka aplikasi → tampil FormLogin
2. Masukkan username dan password
3. Klik tombol "Masuk" atau tekan Enter
4. Sistem akan mengecek kredensial ke database:
   - Jika **Admin** → masuk ke FormMenuUtama
   - Jika **Kurir** → masuk ke FormDashboardKurir
   - Jika salah → tampil pesan error "Username atau password salah"

**Akun Default:**
| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | Admin |
| budi | budi123 | Kurir |
| andi | andi123 | Kurir |
| citra | citra123 | Kurir |

---

### 6.2 Dashboard Admin

Setelah login sebagai Admin, tampil halaman utama dengan:
- **Sidebar kiri**: Navigasi (Dashboard, Kelola Kurir, Kelola Paket, Logout)
- **Content area kanan**: Berganti sesuai menu yang dipilih

#### Panel Dashboard:
- Menampilkan ringkasan: Total Kurir, Total Paket, Paket Terkirim
- Data diambil langsung dari database saat panel dibuka

---

### 6.3 Kelola Kurir (Admin)

#### Menambah Kurir Baru:
1. Klik menu "Kelola Kurir" di sidebar
2. Isi form: Nama Kurir, No Plat, No HP
3. Isi bagian Akun Login: Username, Password
4. Klik tombol "Simpan"
5. Jika berhasil → data muncul di tabel, form direset
6. Akun login kurir otomatis dibuat

#### Mengubah Data Kurir:
1. Klik baris data di tabel → form terisi otomatis
2. Ubah data yang diinginkan
3. Klik tombol "Ubah"
4. Data di database diperbarui

#### Menghapus Kurir:
1. Klik baris data di tabel
2. Klik tombol "Hapus"
3. Konfirmasi "Yakin ingin menghapus?"
4. Jika Ya → data kurir dan akun loginnya dihapus

#### Clear Form:
- Klik tombol "Clear" untuk mengosongkan semua field dan membatalkan seleksi

---

### 6.4 Kelola Paket (Admin)

#### Menambah Paket Baru:
1. Klik menu "Kelola Paket" di sidebar
2. Isi form:
   - No Resi (unik, tidak boleh duplikat)
   - Nama Pengirim
   - Nama Penerima
   - Alamat Tujuan
   - Nama Barang
   - Jenis Layanan (pilih: Reguler/Express/Cargo)
   - Berat (kg) — angka desimal
   - Kurir (pilih dari dropdown)
3. **Total Biaya otomatis terhitung** saat jenis layanan atau berat diubah
4. Status otomatis "Diproses" untuk paket baru
5. Klik "Simpan"

#### Mengubah Data Paket:
1. Klik baris di tabel → form terisi, No Resi terkunci (tidak bisa diedit)
2. Ubah data yang diinginkan
3. Status bisa diubah saat mode edit (ComboBox aktif)
4. Klik "Ubah"

#### Menghapus Paket:
1. Klik baris di tabel
2. Klik "Hapus" → konfirmasi → data dihapus

#### Perhitungan Ongkir Otomatis:
- Setiap kali **Jenis Layanan** atau **Berat** berubah, label "Total Biaya" langsung terupdate
- Rumus: lihat tabel di bagian Schema Database

---

### 6.5 Dashboard Kurir

Setelah login sebagai Kurir:

#### Melihat Paket:
- Tabel hanya menampilkan paket yang ditugaskan ke kurir yang sedang login
- Filter otomatis berdasarkan `id_kurir` dari akun yang login

#### Update Status Paket:
1. Klik baris paket di tabel
2. ComboBox "Update Status" otomatis menunjukkan status saat ini
3. Pilih status baru:
   - Diproses → Sedang Dikirim
   - Sedang Dikirim → Terkirim
4. Klik tombol "Update Status"
5. Jika berhasil → tabel direfresh dengan status baru

#### Batasan Kurir:
- Kurir **TIDAK BISA** menambah, mengubah, atau menghapus data paket
- Kurir **HANYA BISA** mengupdate status paket miliknya sendiri
- Jika mencoba update paket milik kurir lain → ditolak oleh sistem

---

### 6.6 Logout

- Klik tombol "Logout" (di sidebar untuk Admin, di header untuk Kurir)
- Konfirmasi "Yakin ingin logout?"
- Jika Ya → kembali ke FormLogin

---

## 7. STRUKTUR KODE

```
src/
├── koneksi/
│   └── KoneksiDB.java              → Koneksi JDBC ke MySQL
│
├── model/
│   ├── IDAO.java                   → Interface DAO (insert, update, delete, getAll)
│   ├── User.java                   → Entity User (id, username, password, role, id_kurir)
│   ├── UserDAO.java                → DAO User (findByCredentials, isUsernameExist, dll)
│   ├── Kurir.java                  → Entity Kurir (id, nama, plat, hp)
│   ├── KurirDAO.java               → DAO Kurir (insertAndGetId, getAll, dll)
│   ├── Paket.java                  → Entity Paket (resi, pengirim, penerima, dll)
│   ├── PaketDAO.java               → DAO Paket (getByKurir, updateStatusByKurir, dll)
│   ├── LayananEkspedisi.java       → Abstract class (hitungOngkir abstract)
│   ├── LayananReguler.java         → Extends LayananEkspedisi (berat × 10000)
│   ├── LayananExpress.java         → Extends LayananEkspedisi (berat × 15000)
│   └── LayananCargo.java           → Extends LayananEkspedisi (berat × 5000)
│
├── controller/
│   ├── LoginController.java        → login(username, password) → User
│   ├── KurirController.java        → insertWithAccount, update, delete, loadTable
│   └── PaketController.java        → insert, update, delete, hitungOngkir, loadTable
│
└── view/
    ├── AppStyles.java              → Konstanta warna, font, styling tabel
    ├── FormLogin.java              → Form login
    ├── FormMenuUtama.java          → Frame admin (sidebar + CardLayout)
    ├── PanelDashboard.java         → Panel ringkasan data
    ├── PanelKurir.java             → Panel CRUD kurir
    ├── PanelPaket.java             → Panel CRUD paket
    └── FormDashboardKurir.java     → Frame dashboard kurir
```

---

## 8. IMPLEMENTASI OOP

### 8.1 Encapsulation
Semua field di class entity bersifat `private` dan diakses melalui getter/setter:

```java
// Contoh di Kurir.java
private int id_kurir;
private String nama_kurir;
private String no_plat;
private String no_hp;

public int getId_kurir() { return id_kurir; }
public void setId_kurir(int id_kurir) { this.id_kurir = id_kurir; }
// ... dst
```

Diterapkan di: `User.java`, `Kurir.java`, `Paket.java`

### 8.2 Abstraction
Class abstract `LayananEkspedisi` mendefinisikan kontrak tanpa implementasi:

```java
public abstract class LayananEkspedisi {
    private String namaLayanan;

    public LayananEkspedisi(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public abstract double hitungOngkir(double berat);
}
```

### 8.3 Inheritance
Tiga class turunan meng-extend `LayananEkspedisi`:

```java
public class LayananReguler extends LayananEkspedisi {
    public LayananReguler() { super("Reguler"); }
    @Override
    public double hitungOngkir(double berat) { return berat * 10000; }
}

public class LayananExpress extends LayananEkspedisi {
    public LayananExpress() { super("Express"); }
    @Override
    public double hitungOngkir(double berat) { return berat * 15000; }
}

public class LayananCargo extends LayananEkspedisi {
    public LayananCargo() { super("Cargo"); }
    @Override
    public double hitungOngkir(double berat) { return berat * 5000; }
}
```

### 8.4 Polymorphism
Method `hitungOngkir` dipanggil secara polimorfis melalui factory method:

```java
// Di LayananEkspedisi.java
public static LayananEkspedisi fromJenis(String jenis) {
    switch (jenis) {
        case "Express": return new LayananExpress();
        case "Cargo":   return new LayananCargo();
        default:        return new LayananReguler();
    }
}

// Di PaketController.java — polymorphism in action
public double hitungOngkir(String jenisLayanan, double berat) {
    LayananEkspedisi layanan = LayananEkspedisi.fromJenis(jenisLayanan);
    return layanan.hitungOngkir(berat); // method yang dipanggil tergantung tipe objek
}
```

### 8.5 Interface
Interface `IDAO` mendefinisikan kontrak untuk semua class DAO:

```java
public interface IDAO {
    void insert();
    void update();
    void delete();
    List getAll();
}
```

Diimplementasikan oleh: `UserDAO`, `KurirDAO`, `PaketDAO`

```java
public class KurirDAO implements IDAO {
    @Override public void insert() { /* ... */ }
    @Override public void update() { /* ... */ }
    @Override public void delete() { /* ... */ }
    @Override public List<Kurir> getAll() { /* ... */ }
}
```

---

## CATATAN TAMBAHAN

### Keamanan:
- Password disimpan dalam plain text (untuk keperluan tugas/demo)
- Dalam produksi, gunakan hashing (BCrypt/SHA-256)
- Prepared Statement digunakan di semua query untuk mencegah SQL Injection

### Validasi:
- Semua field wajib diisi sebelum simpan/ubah
- Berat harus berupa angka valid
- No Resi harus unik (dicek sebelum insert)
- Username harus unik (dicek sebelum insert)
- Kurir hanya bisa update status paket miliknya sendiri

### Error Handling:
- Semua operasi database dibungkus try-catch
- Error ditampilkan via JOptionPane ke user
- Koneksi menggunakan try-with-resources (auto-close)

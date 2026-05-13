# PETUNJUK PEMBUATAN UI DENGAN NETBEANS FORM DESIGNER (DRAG & DROP) — v2 MULTI-ROLE

Dokumen ini berisi daftar komponen, **variable name**, properti, dan kode listener yang harus kamu buat untuk tiap form di package `view`. Variable name WAJIB sama persis karena dipakai oleh controller.

> **Update v2:** ada **2 role login (Admin & Kurir)** dan form baru `FormDashboardKurir`. Kolom `status_paket` ditambahkan ke tabel paket.

---

## 0. PERSIAPAN AWAL

### Library yang harus ditambahkan
- `mysql-connector-java.jar`

**Cara:** klik kanan project → Properties → Libraries → Add JAR/Folder

### Struktur database terbaru

```sql
DROP DATABASE IF EXISTS db_ekspedisi;
CREATE DATABASE db_ekspedisi;
USE db_ekspedisi;

CREATE TABLE tabel_kurir (
    id_kurir INT AUTO_INCREMENT PRIMARY KEY,
    nama_kurir VARCHAR(100),
    no_plat VARCHAR(20),
    no_hp VARCHAR(15)
);

CREATE TABLE tabel_user (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50),
    role VARCHAR(20),
    id_kurir INT NULL,
    FOREIGN KEY (id_kurir) REFERENCES tabel_kurir(id_kurir)
);

CREATE TABLE tabel_paket (
    no_resi VARCHAR(30) PRIMARY KEY,
    nama_pengirim VARCHAR(100),
    nama_penerima VARCHAR(100),
    alamat_tujuan TEXT,
    jenis_layanan VARCHAR(50),
    berat_kg DOUBLE,
    total_biaya DOUBLE,
    status_paket VARCHAR(50),
    id_kurir INT,
    FOREIGN KEY (id_kurir) REFERENCES tabel_kurir(id_kurir)
);

-- Data contoh
INSERT INTO tabel_kurir (nama_kurir, no_plat, no_hp) VALUES
('Budi Santoso', 'B 1234 XYZ', '081234567890'),
('Agus Wijaya', 'B 5678 ABC', '085678901234');

INSERT INTO tabel_user (username, password, role, id_kurir) VALUES
('admin', 'admin', 'Admin', NULL),
('budi', 'budi123', 'Kurir', 1),
('agus', 'agus123', 'Kurir', 2);
```

### Cara bikin JFrame Form baru di NetBeans
Klik kanan package `view` → New → **JFrame Form** → masukkan nama class.

### Cara rename variable name komponen
Klik kanan komponen di Design view → **Change Variable Name** → ketik nama sesuai petunjuk di bawah.

### Cara tambah event listener
Klik kanan komponen → Events → Action → `actionPerformed` (untuk tombol) atau Mouse → `mouseClicked` (untuk tabel).

---

## 1. FormLogin.java (Multi-Role)

**JFrame Form baru → nama class: `FormLogin`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Login - Sistem Manajemen Ekspedisi` |
| defaultCloseOperation | `EXIT_ON_CLOSE` |
| resizable | `false` |

### Komponen
| Komponen | Variable Name | Text |
|---|---|---|
| JLabel | `lblJudul` | `SISTEM MANAJEMEN EKSPEDISI` |
| JLabel | `lblUsername` | `Username` |
| JTextField | `txtUsername` | (kosong) |
| JLabel | `lblPassword` | `Password` |
| JPasswordField | `txtPassword` | (kosong) |
| JButton | `btnLogin` | `Login` |
| JButton | `btnKeluar` | `Keluar` |

### Event Listener

`btnLogin` → Events → Action:
```java
private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
    String username = txtUsername.getText().trim();
    String password = new String(txtPassword.getPassword());

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    controller.LoginController login = new controller.LoginController();
    model.User user = login.login(username, password);

    if (user == null) {
        JOptionPane.showMessageDialog(this, "Username atau password salah.",
                "Login Gagal", JOptionPane.ERROR_MESSAGE);
        txtPassword.setText("");
        return;
    }

    JOptionPane.showMessageDialog(this,
            "Login berhasil. Selamat datang, " + user.getUsername() + " (" + user.getRole() + ")");

    if (user.isAdmin()) {
        new FormMenuUtama(user).setVisible(true);
    } else if (user.isKurir()) {
        if (user.getId_kurir() == null) {
            JOptionPane.showMessageDialog(this,
                    "Akun kurir belum ditautkan ke data kurir. Hubungi admin.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new FormDashboardKurir(user).setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this,
                "Role tidak dikenali: " + user.getRole(),
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    dispose();
}
```

`btnKeluar` → Events → Action:
```java
private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
    System.exit(0);
}
```

---

## 2. FormMenuUtama.java (Khusus Admin)

**JFrame Form baru → nama class: `FormMenuUtama`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Menu Utama - Admin` |
| defaultCloseOperation | `EXIT_ON_CLOSE` |

### Komponen
| Komponen | Variable Name | Text |
|---|---|---|
| JLabel | `lblHeader` | `Dashboard Admin` |
| JLabel | `lblWelcome` | `Halo, Admin` |
| JButton | `btnKurir` | `Manajemen Kurir` |
| JButton | `btnPaket` | `Manajemen Paket` |
| JButton | `btnLogout` | `Logout` |

### Tambahkan field & constructor (ketik manual)
```java
private model.User currentUser;

public FormMenuUtama() {
    initComponents();
}

public FormMenuUtama(model.User user) {
    initComponents();
    this.currentUser = user;
    lblWelcome.setText("Halo, " + user.getUsername());
}
```

### Event Listener

`btnKurir` → Action:
```java
private void btnKurirActionPerformed(java.awt.event.ActionEvent evt) {
    new FormKurir().setVisible(true);
}
```

`btnPaket` → Action:
```java
private void btnPaketActionPerformed(java.awt.event.ActionEvent evt) {
    new FormPaket().setVisible(true);
}
```

`btnLogout` → Action:
```java
private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
    int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        new FormLogin().setVisible(true);
        dispose();
    }
}
```

---

## 3. FormKurir.java (Admin)

**JFrame Form baru → nama class: `FormKurir`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Manajemen Kurir` |
| defaultCloseOperation | `DISPOSE_ON_CLOSE` |

### Komponen
| Komponen | Variable Name | Text / Properti |
|---|---|---|
| JLabel | `lblId` | `ID Kurir` |
| JTextField | `txtId` | `editable = false` |
| JLabel | `lblNama` | `Nama Kurir` |
| JTextField | `txtNama` | (kosong) |
| JLabel | `lblPlat` | `No Plat` |
| JTextField | `txtPlat` | (kosong) |
| JLabel | `lblHp` | `No HP` |
| JTextField | `txtHp` | (kosong) |
| JButton | `btnTambah` | `Tambah` |
| JButton | `btnUbah` | `Ubah` |
| JButton | `btnHapus` | `Hapus` |
| JButton | `btnBersihkan` | `Bersihkan` |
| JButton | `btnTutup` | `Tutup` |
| JScrollPane + JTable | `tblKurir` | (default) |

### Field & constructor tambahan
```java
private final controller.KurirController kurirCtrl = new controller.KurirController();

public FormKurir() {
    initComponents();
    kurirCtrl.loadTable(tblKurir);
}
```

### Event Listener

`btnTambah` → Action:
```java
private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {
    if (txtNama.getText().trim().isEmpty()
            || txtPlat.getText().trim().isEmpty()
            || txtHp.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field wajib diisi.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    model.Kurir k = new model.Kurir(
            txtNama.getText().trim(),
            txtPlat.getText().trim(),
            txtHp.getText().trim()
    );
    kurirCtrl.insert(k);
    kurirCtrl.loadTable(tblKurir);
    bersihkanForm();
}
```

`btnUbah` → Action:
```java
private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {
    if (txtId.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    model.Kurir k = new model.Kurir(
            Integer.parseInt(txtId.getText().trim()),
            txtNama.getText().trim(),
            txtPlat.getText().trim(),
            txtHp.getText().trim()
    );
    kurirCtrl.update(k);
    kurirCtrl.loadTable(tblKurir);
    bersihkanForm();
}
```

`btnHapus` → Action:
```java
private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
    if (txtId.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus data kurir ini?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) return;

    model.Kurir k = new model.Kurir();
    k.setId_kurir(Integer.parseInt(txtId.getText().trim()));
    kurirCtrl.delete(k);
    kurirCtrl.loadTable(tblKurir);
    bersihkanForm();
}
```

`btnBersihkan` → Action: `bersihkanForm();`
`btnTutup` → Action: `dispose();`

`tblKurir` → Events → Mouse → mouseClicked:
```java
private void tblKurirMouseClicked(java.awt.event.MouseEvent evt) {
    int row = tblKurir.getSelectedRow();
    if (row < 0) return;
    javax.swing.table.DefaultTableModel m = (javax.swing.table.DefaultTableModel) tblKurir.getModel();
    txtId.setText(String.valueOf(m.getValueAt(row, 0)));
    txtNama.setText(String.valueOf(m.getValueAt(row, 1)));
    txtPlat.setText(String.valueOf(m.getValueAt(row, 2)));
    txtHp.setText(String.valueOf(m.getValueAt(row, 3)));
}
```

Helper:
```java
private void bersihkanForm() {
    txtId.setText("");
    txtNama.setText("");
    txtPlat.setText("");
    txtHp.setText("");
    tblKurir.clearSelection();
}
```

---

## 4. FormPaket.java (Admin)

**JFrame Form baru → nama class: `FormPaket`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Manajemen Paket` |
| defaultCloseOperation | `DISPOSE_ON_CLOSE` |

### Komponen
| Komponen | Variable Name | Text / Properti |
|---|---|---|
| JLabel | `lblResi` | `No Resi` |
| JTextField | `txtResi` | (kosong) |
| JLabel | `lblPengirim` | `Nama Pengirim` |
| JTextField | `txtPengirim` | (kosong) |
| JLabel | `lblPenerima` | `Nama Penerima` |
| JTextField | `txtPenerima` | (kosong) |
| JLabel | `lblAlamat` | `Alamat Tujuan` |
| JTextArea | `txtAlamat` | di dalam JScrollPane, lineWrap=true |
| JLabel | `lblLayanan` | `Jenis Layanan` |
| JComboBox | `cbLayanan` | model: `Reguler, Express, Cargo` |
| JLabel | `lblBerat` | `Berat (kg)` |
| JTextField | `txtBerat` | (kosong) |
| JButton | `btnHitung` | `Hitung Ongkir` |
| JLabel | `lblTotalCaption` | `Total Biaya` |
| JLabel | `lblTotalBiaya` | `Rp 0` |
| JLabel | `lblStatusCaption` | `Status Paket` |
| JComboBox | `cbStatus` | model: `Diproses, Sedang Dikirim, Terkirim` |
| JLabel | `lblKurir` | `Kurir` |
| JComboBox | `cbKurir` | (kosong) |
| JButton | `btnTambah` | `Tambah` |
| JButton | `btnUbah` | `Ubah` |
| JButton | `btnHapus` | `Hapus` |
| JButton | `btnBersihkan` | `Bersihkan` |
| JButton | `btnTutup` | `Tutup` |
| JScrollPane + JTable | `tblPaket` | (default) |

**Model JComboBox (custom code):**
- `cbLayanan`: `new javax.swing.DefaultComboBoxModel<>(new String[] { "Reguler", "Express", "Cargo" })`
- `cbStatus`: `new javax.swing.DefaultComboBoxModel<>(new String[] { "Diproses", "Sedang Dikirim", "Terkirim" })`

### Field & import
```java
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

private final controller.PaketController paketCtrl = new controller.PaketController();
private final controller.KurirController kurirCtrl = new controller.KurirController();
private final NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
private List<model.Kurir> daftarKurir;

public FormPaket() {
    initComponents();
    loadKurirCombo();
    paketCtrl.loadTable(tblPaket);
}
```

### Event Listener

`btnHitung` → Action:
```java
private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {
    Double berat = parseBerat();
    if (berat == null) return;
    String jenis = (String) cbLayanan.getSelectedItem();
    double total = paketCtrl.hitungOngkir(jenis, berat);
    lblTotalBiaya.setText(rupiah.format(total));
}
```

`btnTambah` → Action:
```java
private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {
    if (!validateInput()) return;
    Double berat = parseBerat();
    if (berat == null) return;

    String resi = txtResi.getText().trim();
    if (paketCtrl.isResiExist(resi)) {
        JOptionPane.showMessageDialog(this, "No Resi '" + resi + "' sudah terdaftar.",
                "Duplikat", JOptionPane.WARNING_MESSAGE);
        return;
    }
    // Status otomatis di-set ke 'Diproses' oleh controller
    paketCtrl.insert(resi,
            txtPengirim.getText().trim(),
            txtPenerima.getText().trim(),
            txtAlamat.getText().trim(),
            (String) cbLayanan.getSelectedItem(),
            berat,
            getSelectedKurirId());
    paketCtrl.loadTable(tblPaket);
    bersihkanForm();
}
```

`btnUbah` → Action:
```java
private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {
    if (txtResi.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (!validateInput()) return;
    Double berat = parseBerat();
    if (berat == null) return;

    paketCtrl.update(txtResi.getText().trim(),
            txtPengirim.getText().trim(),
            txtPenerima.getText().trim(),
            txtAlamat.getText().trim(),
            (String) cbLayanan.getSelectedItem(),
            berat,
            (String) cbStatus.getSelectedItem(),
            getSelectedKurirId());
    paketCtrl.loadTable(tblPaket);
    bersihkanForm();
}
```

`btnHapus` → Action:
```java
private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
    if (txtResi.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus paket ini?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) return;
    paketCtrl.delete(txtResi.getText().trim());
    paketCtrl.loadTable(tblPaket);
    bersihkanForm();
}
```

`btnBersihkan` → Action: `bersihkanForm();`
`btnTutup` → Action: `dispose();`

`tblPaket` → Events → Mouse → mouseClicked:
```java
private void tblPaketMouseClicked(java.awt.event.MouseEvent evt) {
    int row = tblPaket.getSelectedRow();
    if (row < 0) return;
    javax.swing.table.DefaultTableModel m = (javax.swing.table.DefaultTableModel) tblPaket.getModel();
    txtResi.setText(String.valueOf(m.getValueAt(row, 0)));
    txtResi.setEditable(false);
    txtPengirim.setText(String.valueOf(m.getValueAt(row, 1)));
    txtPenerima.setText(String.valueOf(m.getValueAt(row, 2)));
    txtAlamat.setText(String.valueOf(m.getValueAt(row, 3)));
    cbLayanan.setSelectedItem(String.valueOf(m.getValueAt(row, 4)));
    txtBerat.setText(String.valueOf(m.getValueAt(row, 5)));
    double total = ((Number) m.getValueAt(row, 6)).doubleValue();
    lblTotalBiaya.setText(rupiah.format(total));
    cbStatus.setSelectedItem(String.valueOf(m.getValueAt(row, 7)));
    selectKurirByName(String.valueOf(m.getValueAt(row, 8)));
}
```

### Helper methods
```java
private void loadKurirCombo() {
    daftarKurir = kurirCtrl.getAll();
    cbKurir.removeAllItems();
    for (model.Kurir k : daftarKurir) {
        cbKurir.addItem(k.getId_kurir() + " - " + k.getNama_kurir());
    }
}

private void selectKurirByName(String nama) {
    for (int i = 0; i < daftarKurir.size(); i++) {
        if (daftarKurir.get(i).getNama_kurir().equals(nama)) {
            cbKurir.setSelectedIndex(i);
            return;
        }
    }
}

private int getSelectedKurirId() {
    int idx = cbKurir.getSelectedIndex();
    if (idx < 0 || idx >= daftarKurir.size()) return -1;
    return daftarKurir.get(idx).getId_kurir();
}

private Double parseBerat() {
    try {
        double berat = Double.parseDouble(txtBerat.getText().trim());
        if (berat <= 0) {
            JOptionPane.showMessageDialog(this, "Berat harus lebih dari 0.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return berat;
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Berat harus berupa angka.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return null;
    }
}

private boolean validateInput() {
    if (txtResi.getText().trim().isEmpty()
            || txtPengirim.getText().trim().isEmpty()
            || txtPenerima.getText().trim().isEmpty()
            || txtAlamat.getText().trim().isEmpty()
            || txtBerat.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field wajib diisi.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    if (getSelectedKurirId() < 0) {
        JOptionPane.showMessageDialog(this, "Pilih kurir terlebih dahulu.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    return true;
}

private void bersihkanForm() {
    txtResi.setText("");
    txtResi.setEditable(true);
    txtPengirim.setText("");
    txtPenerima.setText("");
    txtAlamat.setText("");
    cbLayanan.setSelectedIndex(0);
    txtBerat.setText("");
    lblTotalBiaya.setText(rupiah.format(0));
    cbStatus.setSelectedIndex(0);
    if (cbKurir.getItemCount() > 0) cbKurir.setSelectedIndex(0);
    tblPaket.clearSelection();
}
```

---

## 5. FormDashboardKurir.java (BARU, Khusus Kurir)

**JFrame Form baru → nama class: `FormDashboardKurir`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Dashboard Kurir` |
| defaultCloseOperation | `EXIT_ON_CLOSE` |

### Komponen

**Panel header / info kurir:**
| Komponen | Variable Name | Text |
|---|---|---|
| JLabel | `lblHeader` | `Dashboard Kurir` |
| JLabel | `lblInfoKurir` | `Halo, Nama Kurir` |

**Panel detail paket yang terpilih:**
| Komponen | Variable Name | Text / Properti |
|---|---|---|
| JLabel | `lblResi` | `No Resi` |
| JTextField | `txtResi` | `editable = false` |
| JLabel | `lblPenerima` | `Penerima` |
| JTextField | `txtPenerima` | `editable = false` |
| JLabel | `lblAlamat` | `Alamat` |
| JTextArea | `txtAlamat` | di dalam JScrollPane, `editable = false` |
| JLabel | `lblStatusCaption` | `Status Saat Ini` |
| JTextField | `txtStatus` | `editable = false` |
| JLabel | `lblUpdateStatus` | `Ubah Status` |
| JComboBox | `cbStatus` | model: `Diproses, Sedang Dikirim, Terkirim` |
| JButton | `btnUpdateStatus` | `Update Status` |
| JButton | `btnRefresh` | `Refresh` |
| JButton | `btnLogout` | `Logout` |

**Tabel:**
| Komponen | Variable Name |
|---|---|
| JScrollPane + JTable | `tblPaket` |

### Field & constructor
```java
private model.User currentUser;
private final controller.PaketController paketCtrl = new controller.PaketController();

public FormDashboardKurir() {
    initComponents();
}

public FormDashboardKurir(model.User user) {
    initComponents();
    this.currentUser = user;
    lblInfoKurir.setText("Halo, " + user.getUsername());
    if (user.getId_kurir() != null) {
        paketCtrl.loadTableByKurir(tblPaket, user.getId_kurir());
    }
}
```

### Event Listener

`tblPaket` → Events → Mouse → mouseClicked:
```java
private void tblPaketMouseClicked(java.awt.event.MouseEvent evt) {
    int row = tblPaket.getSelectedRow();
    if (row < 0) return;
    javax.swing.table.DefaultTableModel m = (javax.swing.table.DefaultTableModel) tblPaket.getModel();
    txtResi.setText(String.valueOf(m.getValueAt(row, 0)));
    txtPenerima.setText(String.valueOf(m.getValueAt(row, 2)));
    txtAlamat.setText(String.valueOf(m.getValueAt(row, 3)));
    txtStatus.setText(String.valueOf(m.getValueAt(row, 7)));
    cbStatus.setSelectedItem(String.valueOf(m.getValueAt(row, 7)));
}
```

`btnUpdateStatus` → Action:
```java
private void btnUpdateStatusActionPerformed(java.awt.event.ActionEvent evt) {
    if (txtResi.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih paket dari tabel terlebih dahulu.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    String statusBaru = (String) cbStatus.getSelectedItem();
    paketCtrl.updateStatus(txtResi.getText().trim(), statusBaru);
    if (currentUser != null && currentUser.getId_kurir() != null) {
        paketCtrl.loadTableByKurir(tblPaket, currentUser.getId_kurir());
    }
    bersihkan();
}
```

`btnRefresh` → Action:
```java
private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
    if (currentUser != null && currentUser.getId_kurir() != null) {
        paketCtrl.loadTableByKurir(tblPaket, currentUser.getId_kurir());
    }
    bersihkan();
}
```

`btnLogout` → Action:
```java
private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
    int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        new FormLogin().setVisible(true);
        dispose();
    }
}
```

Helper:
```java
private void bersihkan() {
    txtResi.setText("");
    txtPenerima.setText("");
    txtAlamat.setText("");
    txtStatus.setText("");
    cbStatus.setSelectedIndex(0);
    tblPaket.clearSelection();
}
```

---

## 6. RINGKASAN VARIABLE NAME WAJIB

### FormLogin
`txtUsername`, `txtPassword`, `btnLogin`, `btnKeluar`

### FormMenuUtama (Admin)
`btnKurir`, `btnPaket`, `btnLogout`, `lblWelcome`

### FormKurir (Admin)
`txtId`, `txtNama`, `txtPlat`, `txtHp`, `btnTambah`, `btnUbah`, `btnHapus`, `btnBersihkan`, `btnTutup`, `tblKurir`

### FormPaket (Admin)
`txtResi`, `txtPengirim`, `txtPenerima`, `txtAlamat`, `cbLayanan`, `txtBerat`, `btnHitung`, `lblTotalBiaya`, `cbStatus`, `cbKurir`, `btnTambah`, `btnUbah`, `btnHapus`, `btnBersihkan`, `btnTutup`, `tblPaket`

### FormDashboardKurir (Kurir)
`lblInfoKurir`, `txtResi`, `txtPenerima`, `txtAlamat`, `txtStatus`, `cbStatus`, `btnUpdateStatus`, `btnRefresh`, `btnLogout`, `tblPaket`

---

## 7. ENTRY POINT

Set main class project ke `view.FormLogin`:
**Klik kanan project → Properties → Run → Main Class → `view.FormLogin`**

---

## 8. LAYOUT TIPS (opsional)

- **Free Design** (default NetBeans) — drag komponen, ikuti garis biru panduan.
- Kelompokkan komponen dengan **JPanel** (Titled Border agar rapi).
- JTable WAJIB dalam **JScrollPane**; drag JScrollPane dulu, lalu drag JTable ke dalamnya.
- JTextArea (`txtAlamat`) juga dalam **JScrollPane**.

---

## 9. KOLOM TABEL PAKET (untuk reference saat MouseListener)

Index kolom di JTable paket (setelah `loadTable` / `loadTableByKurir`):

| Index | Kolom |
|---|---|
| 0 | No Resi |
| 1 | Pengirim |
| 2 | Penerima |
| 3 | Alamat Tujuan |
| 4 | Layanan |
| 5 | Berat (kg) |
| 6 | Total Biaya |
| 7 | Status |
| 8 | Kurir |

---

## 10. FILE YANG TIDAK BOLEH DIHAPUS

```
koneksi/
  KoneksiDB.java
model/
  User.java                ← field: id_user, username, password, role, id_kurir
  Kurir.java
  Paket.java               ← field: + status_paket
  IDAO.java
  KurirDAO.java
  PaketDAO.java            ← method: getByKurir(), updateStatus()
  UserDAO.java             ← BARU: findByCredentials()
  LayananEkspedisi.java
  LayananReguler.java
  LayananExpress.java
  LayananCargo.java
controller/
  LoginController.java     ← method: login() return User
  KurirController.java
  PaketController.java     ← method: loadTableByKurir(), updateStatus(), insert auto-status
```

Package `view` kosong — tinggal kamu drag & drop sesuai petunjuk di atas.

# PETUNJUK PEMBUATAN UI DENGAN NETBEANS FORM DESIGNER (DRAG & DROP)

Dokumen ini berisi daftar komponen, **variable name**, properti, dan kode listener yang harus kamu buat untuk tiap form di package `view`. Ikuti urutannya — variable name WAJIB sama persis karena dipakai oleh controller.

---

## 0. PERSIAPAN AWAL

### Library yang harus ditambahkan
1. `mysql-connector-java.jar`
2. `flatlaf-3.x.x.jar` (download dari https://github.com/JFormDesigner/FlatLaf/releases)

**Cara:** klik kanan project → Properties → Libraries → Add JAR/Folder

### Cara bikin JFrame Form baru di NetBeans
Klik kanan package `view` → New → **JFrame Form** → masukkan nama class.

### Cara rename variable name komponen
Klik kanan komponen di Design view → **Change Variable Name** → ketik nama sesuai petunjuk di bawah.

### Cara ubah properti
Pilih komponen → panel **Properties** di kanan bawah → ubah `text`, `font`, dll.

### Cara tambah event listener
Klik kanan komponen → Events → Action → `actionPerformed` (untuk tombol) atau Mouse → `mouseClicked` (untuk tabel).

---

## 1. FormLogin.java

**JFrame Form baru → nama class: `FormLogin`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Login - Sistem Manajemen Ekspedisi` |
| defaultCloseOperation | `EXIT_ON_CLOSE` |
| resizable | `false` |
| size | 420 × 480 |

### Daftar Komponen
| Komponen | Variable Name | Text / Properti |
|---|---|---|
| JPanel (header) | `headerPanel` | background biru |
| JLabel | `lblHeader` | `Sistem Manajemen Ekspedisi` |
| JLabel | `lblSubHeader` | `Silakan login untuk melanjutkan` |
| JLabel | `lblUsername` | `Username` |
| JTextField | `txtUsername` | (kosong) |
| JLabel | `lblPassword` | `Password` |
| JPasswordField | `txtPassword` | (kosong) |
| JButton | `btnLogin` | `Login` |
| JButton | `btnKeluar` | `Keluar` |

### Event Listener yang harus dibuat
Klik kanan `btnLogin` → Events → Action → `actionPerformed`, isi:
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
    if (login.authenticate(username, password)) {
        JOptionPane.showMessageDialog(this, "Login berhasil. Selamat datang, " + username + "!");
        new FormMenuUtama(username).setVisible(true);
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Username atau password salah.",
                "Login Gagal", JOptionPane.ERROR_MESSAGE);
        txtPassword.setText("");
    }
}
```

Klik kanan `btnKeluar` → Events → Action → `actionPerformed`, isi:
```java
private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
    System.exit(0);
}
```

### Method main() — ganti bawaan NetBeans jadi seperti ini
```java
public static void main(String args[]) {
    try {
        javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
    } catch (Exception ex) {
        // fallback default LaF
    }
    java.awt.EventQueue.invokeLater(() -> new FormLogin().setVisible(true));
}
```

---

## 2. FormMenuUtama.java

**JFrame Form baru → nama class: `FormMenuUtama`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Menu Utama - Sistem Manajemen Ekspedisi` |
| defaultCloseOperation | `EXIT_ON_CLOSE` |
| size | 720 × 460 |

### Daftar Komponen
| Komponen | Variable Name | Text / Properti |
|---|---|---|
| JPanel (header) | `headerPanel` | warna biru |
| JLabel | `lblHeader` | `Dashboard` |
| JLabel | `lblWelcome` | `Halo, User` |
| JButton (card) | `btnKurir` | `Manajemen Kurir` |
| JButton (card) | `btnPaket` | `Manajemen Paket` |
| JButton | `btnLogout` | `Logout` |

### Tambahkan field di class (ketik manual di atas method `FormMenuUtama()`)
```java
private String username = "User";

public FormMenuUtama() {
    initComponents();
}

public FormMenuUtama(String username) {
    initComponents();
    this.username = username;
    lblWelcome.setText("Halo, " + username);
}
```

### Event Listener
Klik kanan `btnKurir` → Events → Action:
```java
private void btnKurirActionPerformed(java.awt.event.ActionEvent evt) {
    new FormKurir().setVisible(true);
}
```

Klik kanan `btnPaket` → Events → Action:
```java
private void btnPaketActionPerformed(java.awt.event.ActionEvent evt) {
    new FormPaket().setVisible(true);
}
```

Klik kanan `btnLogout` → Events → Action:
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

## 3. FormKurir.java

**JFrame Form baru → nama class: `FormKurir`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Manajemen Kurir` |
| defaultCloseOperation | `DISPOSE_ON_CLOSE` |
| size | 1000 × 640 |

### Daftar Komponen

**Panel Header (atas):**
| Komponen | Variable Name | Text |
|---|---|---|
| JPanel | `headerPanel` | background biru |
| JLabel | `lblHeader` | `Manajemen Kurir` |
| JLabel | `lblSubHeader` | `Kelola data kurir dan armada pengiriman` |

**Panel Form Input (sebelah kiri / atas):**
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

**Panel Tombol (di bawah form input):**
| Komponen | Variable Name | Text |
|---|---|---|
| JButton | `btnTambah` | `Tambah` |
| JButton | `btnUbah` | `Ubah` |
| JButton | `btnHapus` | `Hapus` |
| JButton | `btnBersihkan` | `Bersihkan` |
| JButton | `btnTutup` | `Tutup` |

**Panel Tabel (sebelah kanan / bawah):**
| Komponen | Variable Name | Properti |
|---|---|---|
| JScrollPane | `scrollTable` | berisi `tblKurir` |
| JTable | `tblKurir` | model default (biarkan, akan di-replace oleh controller) |

### Tambahkan field di class
```java
private final controller.KurirController kurirCtrl = new controller.KurirController();

public FormKurir() {
    initComponents();
    kurirCtrl.loadTable(tblKurir);
}
```

### Event Listener

Klik kanan `btnTambah` → Events → Action:
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

Klik kanan `btnUbah` → Events → Action:
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

Klik kanan `btnHapus` → Events → Action:
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

Klik kanan `btnBersihkan` → Events → Action:
```java
private void btnBersihkanActionPerformed(java.awt.event.ActionEvent evt) {
    bersihkanForm();
}
```

Klik kanan `btnTutup` → Events → Action:
```java
private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {
    dispose();
}
```

Klik kanan `tblKurir` → Events → Mouse → `mouseClicked`:
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

Tambahkan method helper di akhir class:
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

## 4. FormPaket.java

**JFrame Form baru → nama class: `FormPaket`**

### Properti JFrame
| Properti | Nilai |
|---|---|
| title | `Manajemen Paket` |
| defaultCloseOperation | `DISPOSE_ON_CLOSE` |
| size | 1200 × 720 |

### Daftar Komponen

**Panel Header (atas):**
| Komponen | Variable Name | Text |
|---|---|---|
| JPanel | `headerPanel` | background biru |
| JLabel | `lblHeader` | `Manajemen Paket` |
| JLabel | `lblSubHeader` | `Input dan kelola data pengiriman paket` |

**Panel Form Input:**
| Komponen | Variable Name | Text / Properti |
|---|---|---|
| JLabel | `lblResi` | `No Resi` |
| JTextField | `txtResi` | (kosong) |
| JLabel | `lblPengirim` | `Nama Pengirim` |
| JTextField | `txtPengirim` | (kosong) |
| JLabel | `lblPenerima` | `Nama Penerima` |
| JTextField | `txtPenerima` | (kosong) |
| JLabel | `lblAlamat` | `Alamat Tujuan` |
| JTextArea | `txtAlamat` | `lineWrap = true`, `wrapStyleWord = true` — bungkus dengan JScrollPane |
| JLabel | `lblLayanan` | `Jenis Layanan` |
| JComboBox | `cbLayanan` | **model:** `Reguler, Express, Cargo` |
| JLabel | `lblBerat` | `Berat (kg)` |
| JTextField | `txtBerat` | (kosong) |
| JButton | `btnHitung` | `Hitung Ongkir` |
| JLabel | `lblTotalCaption` | `Total Biaya` |
| JLabel | `lblTotalBiaya` | `Rp 0` |
| JLabel | `lblKurir` | `Kurir` |
| JComboBox | `cbKurir` | (kosong, diisi runtime) |

**Cara isi model JComboBox `cbLayanan`:**
Pilih cbLayanan → Properties → `model` → klik tombol `...` → **Custom Code** → masukkan:
```java
new javax.swing.DefaultComboBoxModel<>(new String[] { "Reguler", "Express", "Cargo" })
```

**Panel Tombol:**
| Komponen | Variable Name | Text |
|---|---|---|
| JButton | `btnTambah` | `Tambah` |
| JButton | `btnUbah` | `Ubah` |
| JButton | `btnHapus` | `Hapus` |
| JButton | `btnBersihkan` | `Bersihkan` |
| JButton | `btnTutup` | `Tutup` |

**Panel Tabel:**
| Komponen | Variable Name |
|---|---|
| JScrollPane | `scrollTable` |
| JTable | `tblPaket` |

### Tambahkan field di class (import & deklarasi)
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

`btnHitung` → Events → Action:
```java
private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {
    Double berat = parseBerat();
    if (berat == null) return;
    String jenis = (String) cbLayanan.getSelectedItem();
    double total = paketCtrl.hitungOngkir(jenis, berat);
    lblTotalBiaya.setText(rupiah.format(total));
}
```

`btnTambah` → Events → Action:
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

`btnUbah` → Events → Action:
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
            getSelectedKurirId());
    paketCtrl.loadTable(tblPaket);
    bersihkanForm();
}
```

`btnHapus` → Events → Action:
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

`btnBersihkan` → Events → Action:
```java
private void btnBersihkanActionPerformed(java.awt.event.ActionEvent evt) {
    bersihkanForm();
}
```

`btnTutup` → Events → Action:
```java
private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {
    dispose();
}
```

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
    selectKurirByName(String.valueOf(m.getValueAt(row, 7)));
}
```

### Method helper — tambahkan di bawah event handler
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
    if (cbKurir.getItemCount() > 0) cbKurir.setSelectedIndex(0);
    tblPaket.clearSelection();
}
```

---

## 5. RINGKASAN VARIABLE NAME (WAJIB SAMA PERSIS)

### FormLogin
`txtUsername`, `txtPassword`, `btnLogin`, `btnKeluar`

### FormMenuUtama
`btnKurir`, `btnPaket`, `btnLogout`, `lblWelcome`

### FormKurir
`txtId`, `txtNama`, `txtPlat`, `txtHp`, `btnTambah`, `btnUbah`, `btnHapus`, `btnBersihkan`, `btnTutup`, `tblKurir`

### FormPaket
`txtResi`, `txtPengirim`, `txtPenerima`, `txtAlamat`, `cbLayanan`, `txtBerat`, `btnHitung`, `lblTotalBiaya`, `cbKurir`, `btnTambah`, `btnUbah`, `btnHapus`, `btnBersihkan`, `btnTutup`, `tblPaket`

---

## 6. ENTRY POINT

Set main class project ke `view.FormLogin`:
**Klik kanan project → Properties → Run → Main Class → `view.FormLogin`**

---

## 7. LAYOUT TIPS (opsional, untuk tampilan rapi)

- Pakai **Free Design** (default NetBeans) — drag komponen, ikuti garis panduan biru yang muncul untuk auto-align.
- Gunakan **JPanel** terpisah untuk mengelompokkan: header, form input, tombol, tabel.
- JTable sebaiknya di dalam **JScrollPane** (drag JScrollPane dulu, lalu drag JTable ke dalamnya).
- JTextArea (untuk `txtAlamat`) juga sebaiknya di dalam **JScrollPane**.
- Border panel: klik kanan panel → Properties → `border` → pilih **Titled Border** atau **Line Border**.

---

## 8. STRUKTUR PACKAGE YANG TIDAK BOLEH DIHAPUS

File-file berikut harus tetap ada (**jangan dihapus**):

```
koneksi/
  KoneksiDB.java           ← sudah diperbarui manual
model/
  User.java
  Kurir.java
  Paket.java
  Orang.java               ← (jika masih ada, bisa dihapus — tidak dipakai)
  IDAO.java
  KurirDAO.java
  PaketDAO.java
  LayananEkspedisi.java
  LayananReguler.java
  LayananExpress.java
  LayananCargo.java
controller/
  LoginController.java
  KurirController.java
  PaketController.java
```

Hanya **package `view`** yang akan dihapus dan dibuat ulang via NetBeans drag & drop.

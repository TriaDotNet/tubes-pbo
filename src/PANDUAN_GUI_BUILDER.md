# PANDUAN BUILD TAMPILAN DI NETBEANS GUI BUILDER

## WARNA TEMA (Teal/Green)
Gunakan warna-warna ini secara konsisten:

| Nama | RGB | Hex | Dipakai untuk |
|------|-----|-----|---------------|
| PRIMARY | (0, 137, 123) | #00897B | Background sidebar, tombol utama, header |
| PRIMARY_DARK | (0, 105, 92) | #00695C | Tombol aktif sidebar, tombol ubah |
| PRIMARY_LIGHT | (178, 223, 219) | #B2DFDB | Selection tabel, teks subtitle |
| BG_MAIN | (250, 250, 250) | #FAFAFA | Background content area |
| TEXT_DARK | (33, 33, 33) | #212121 | Teks utama |
| TEXT_SECONDARY | (117, 117, 117) | #757575 | Teks subtitle, label kecil |
| DANGER | (211, 47, 47) | #D32F2F | Tombol hapus |
| BORDER | (224, 224, 224) | #E0E0E0 | Border panel, tabel |
| TABLE_ALT | (245, 245, 245) | #F5F5F5 | Baris tabel genap |

## FONT
- **Judul**: Segoe UI, Bold, 22
- **Subtitle**: Segoe UI, Bold, 16
- **Body/Label**: Segoe UI, Plain, 14
- **Tombol**: Segoe UI, Bold, 13
- **Tabel**: Segoe UI, Plain, 13
- **Tabel Header**: Segoe UI, Bold, 13

---

## 1. FORM LOGIN (FormLogin.java)

### Tipe: JFrame
- **Variable name**: FormLogin
- **Size**: 420 x 320
- **Resizable**: false
- **Location**: Center screen (setLocationRelativeTo(null))
- **Background**: White (255, 255, 255)
- **Layout**: GridBagLayout

### Komponen:

| No | Komponen | Variable Name | Properties |
|----|----------|---------------|------------|
| 1 | JLabel | lblTitle | Text: "Sistem Ekspedisi", Font: Segoe UI Bold 22, Foreground: (0,137,123), HorizontalAlignment: CENTER |
| 2 | JLabel | lblSubtitle | Text: "Masuk ke akun Anda", Font: Segoe UI Plain 14, Foreground: (117,117,117), HorizontalAlignment: CENTER |
| 3 | JLabel | lblUsername | Text: "Username", Font: Segoe UI Plain 14 |
| 4 | JTextField | txtUsername | Font: Segoe UI Plain 14, PreferredSize: (0, 32) |
| 5 | JLabel | lblPassword | Text: "Password", Font: Segoe UI Plain 14 |
| 6 | JPasswordField | txtPassword | Font: Segoe UI Plain 14, PreferredSize: (0, 32) |
| 7 | JLabel | lblError | Text: " ", Font: Segoe UI Plain 12, Foreground: (211,47,47), HorizontalAlignment: CENTER |
| 8 | JButton | btnLogin | Text: "Masuk", Font: Segoe UI Bold 13, Background: (0,137,123), Foreground: White, PreferredSize: (0, 36) |

### Layout GridBagConstraints:
- Semua komponen: gridx=0, fill=HORIZONTAL, weightx=1.0
- Insets kiri-kanan: 30px
- Urutan gridy: 0=lblTitle, 1=lblSubtitle, 2=lblUsername, 3=txtUsername, 4=lblPassword, 5=txtPassword, 6=lblError, 7=btnLogin
- Spacing: antara title dan subtitle rapat, antara subtitle dan form 16px, antara field 4-10px, antara error dan button 8px

### Event:
- `btnLogin` → ActionListener → panggil method `doLogin()`
- `txtPassword` → KeyListener → jika ENTER, panggil `doLogin()`
- `txtUsername` → KeyListener → jika ENTER, pindah focus ke `txtPassword`

---

## 2. FORM MENU UTAMA / ADMIN (FormMenuUtama.java)

### Tipe: JFrame
- **Variable name**: FormMenuUtama
- **Size**: 1100 x 700
- **Location**: Center screen
- **Layout**: BorderLayout

### Struktur Panel:

```
┌─────────────────────────────────────────────────┐
│ JFrame (BorderLayout)                           │
├──────────┬──────────────────────────────────────┤
│ WEST     │ CENTER                               │
│ sidebar  │ contentPanel (CardLayout)            │
│ 200px    │                                      │
│          │  ┌─ "dashboard" → PanelDashboard     │
│ Brand    │  ├─ "kurir" → PanelKurir             │
│ ───────  │  └─ "paket" → PanelPaket            │
│ Nav btns │                                      │
│          │                                      │
│ ───────  │                                      │
│ Logout   │                                      │
└──────────┴──────────────────────────────────────┘
```

### Panel Sidebar (pnlSidebar)
- **Tipe**: JPanel
- **Variable name**: pnlSidebar
- **PreferredSize**: (200, 0)
- **Background**: (0, 137, 123)
- **Layout**: BorderLayout

#### Komponen Sidebar:

| No | Komponen | Variable Name | Properties |
|----|----------|---------------|------------|
| 1 | JLabel | lblBrand | Text: "Ekspedisi", Font: Segoe UI Bold 20, Foreground: White |
| 2 | JLabel | lblRole | Text: "Panel Admin", Font: Segoe UI Plain 12, Foreground: (178,223,219) |
| 3 | JButton | btnNavDashboard | Text: "Dashboard", Font: Segoe UI Bold 13, Foreground: White, Background: (0,137,123), BorderPainted: false, FocusPainted: false, HorizontalAlignment: LEFT, PreferredSize: (200, 40) |
| 4 | JButton | btnNavKurir | Text: "Kelola Kurir", sama seperti di atas |
| 5 | JButton | btnNavPaket | Text: "Kelola Paket", sama seperti di atas |
| 6 | JButton | btnLogout | Text: "Logout", sama seperti di atas, Foreground: (255, 200, 200) |

#### Sidebar Layout Detail:
- NORTH: Panel berisi lblBrand + lblRole (padding 20,16,20,16)
- CENTER: Panel berisi btnNavDashboard, btnNavKurir, btnNavPaket (BoxLayout Y_AXIS, spacing 2px)
- SOUTH: Panel berisi btnLogout (padding 0,8,12,8)

#### Tombol Aktif:
- Tombol yang sedang aktif: Background = (0, 105, 92) PRIMARY_DARK
- Tombol tidak aktif: Background = (0, 137, 123) PRIMARY

### Panel Content (pnlContent)
- **Tipe**: JPanel
- **Variable name**: pnlContent
- **Layout**: CardLayout
- **Background**: (250, 250, 250)

### Event Sidebar:
- `btnNavDashboard` → cardLayout.show(pnlContent, "dashboard")
- `btnNavKurir` → cardLayout.show(pnlContent, "kurir")
- `btnNavPaket` → cardLayout.show(pnlContent, "paket")
- `btnLogout` → konfirmasi → dispose() → new FormLogin().setVisible(true)

---

## 3. PANEL DASHBOARD (PanelDashboard.java)

### Tipe: JPanel
- **Variable name**: pnlDashboard
- **Background**: (250, 250, 250)
- **Layout**: BorderLayout
- **Border**: EmptyBorder(30, 30, 30, 30)

### Komponen:

| No | Komponen | Variable Name | Properties |
|----|----------|---------------|------------|
| 1 | JLabel | lblWelcome | Text: "Selamat Datang, Admin", Font: Segoe UI Bold 22, Foreground: (33,33,33) |
| 2 | JLabel | lblSubDash | Text: "Sistem Manajemen Ekspedisi", Font: Segoe UI Plain 14, Foreground: (117,117,117) |
| 3 | JPanel | pnlCards | Layout: GridLayout(1, 3, 16, 0), Opaque: false |

### Summary Cards (di dalam pnlCards):

Setiap card adalah JPanel:
- **Layout**: BorderLayout(0, 4)
- **Background**: White
- **Border**: LineBorder (224,224,224) 1px + EmptyBorder(16, 20, 16, 20)

| Card | Variable Name | Label (SOUTH) | Value (CENTER) |
|------|---------------|---------------|----------------|
| 1 | pnlCardKurir | "Total Kurir" | lblTotalKurir (Font: Segoe UI Bold 28, Foreground: PRIMARY) |
| 2 | pnlCardPaket | "Total Paket" | lblTotalPaket (Font: Segoe UI Bold 28, Foreground: PRIMARY) |
| 3 | pnlCardTerkirim | "Terkirim" | lblTotalTerkirim (Font: Segoe UI Bold 28, Foreground: PRIMARY) |

### Posisi:
- lblWelcome + lblSubDash + pnlCards semuanya di NORTH (dalam BoxLayout Y_AXIS wrapper)
- CENTER dibiarkan kosong (agar cards tidak stretch ke bawah)

---

## 4. PANEL KURIR (PanelKurir.java)

### Tipe: JPanel
- **Variable name**: pnlKurir
- **Background**: (250, 250, 250)
- **Layout**: BorderLayout(0, 12)
- **Border**: EmptyBorder(20, 20, 20, 20)

### Struktur:

```
┌──────────────────────────────────────────────┐
│ NORTH: lblTitleKurir                         │
├────────────┬─────────────────────────────────┤
│ WEST       │ CENTER                          │
│ Form Panel │ Table Panel                     │
│ 280px      │                                 │
│            │ ┌─────────────────────────────┐ │
│ Nama       │ │ JScrollPane                 │ │
│ No Plat    │ │   └─ tblKurir               │ │
│ No HP      │ │                             │ │
│ ─────────  │ │                             │ │
│ Akun Login │ │                             │ │
│ Username   │ │                             │ │
│ Password   │ │                             │ │
│ ─────────  │ │                             │ │
│ [Buttons]  │ └─────────────────────────────┘ │
└────────────┴─────────────────────────────────┘
```

### Panel Form (pnlFormKurir) — WEST
- **PreferredSize**: (280, 0)
- **Background**: White
- **Border**: LineBorder (224,224,224) + EmptyBorder(16, 16, 16, 16)
- **Layout**: GridBagLayout

| No | Komponen | Variable Name | Properties |
|----|----------|---------------|------------|
| 1 | JLabel | lblNamaKurir | Text: "Nama Kurir", Font: Segoe UI Plain 14 |
| 2 | JTextField | txtNamaKurir | Font: Segoe UI Plain 14, PreferredSize: (0, 30) |
| 3 | JLabel | lblNoPlat | Text: "No Plat", Font: Segoe UI Plain 14 |
| 4 | JTextField | txtNoPlat | Font: Segoe UI Plain 14, PreferredSize: (0, 30) |
| 5 | JLabel | lblNoHp | Text: "No HP", Font: Segoe UI Plain 14 |
| 6 | JTextField | txtNoHp | Font: Segoe UI Plain 14, PreferredSize: (0, 30) |
| 7 | JLabel | lblAkunTitle | Text: "Akun Login", Font: Segoe UI Bold 16, Foreground: (0,137,123) |
| 8 | JLabel | lblUsernameKurir | Text: "Username", Font: Segoe UI Plain 14 |
| 9 | JTextField | txtUsernameKurir | Font: Segoe UI Plain 14, PreferredSize: (0, 30) |
| 10 | JLabel | lblPasswordKurir | Text: "Password", Font: Segoe UI Plain 14 |
| 11 | JPasswordField | txtPasswordKurir | Font: Segoe UI Plain 14, PreferredSize: (0, 30) |
| 12 | JButton | btnSimpanKurir | Text: "Simpan", Font: Segoe UI Bold 13, Background: (0,137,123), Foreground: White |
| 13 | JButton | btnUbahKurir | Text: "Ubah", Font: Segoe UI Bold 13, Background: (0,105,92), Foreground: White |
| 14 | JButton | btnHapusKurir | Text: "Hapus", Font: Segoe UI Bold 13, Background: (211,47,47), Foreground: White |
| 15 | JButton | btnClearKurir | Text: "Clear", Font: Segoe UI Bold 13 |

#### Button Layout:
- 4 tombol dalam JPanel dengan GridLayout(2, 2, 6, 6)

### Panel Tabel (pnlTableKurir) — CENTER
- **Border**: EmptyBorder(0, 12, 0, 0)
- **Layout**: BorderLayout

| Komponen | Variable Name | Properties |
|----------|---------------|------------|
| JTable | tblKurir | Font: Segoe UI Plain 13, RowHeight: 30, SelectionMode: SINGLE_SELECTION |
| JScrollPane | spKurir | Border: LineBorder (224,224,224) |

#### Kolom Tabel tblKurir:
| # | Nama Kolom | Lebar (approx) |
|---|-----------|----------------|
| 0 | ID Kurir | 60 |
| 1 | Nama Kurir | 150 |
| 2 | No Plat | 100 |
| 3 | No HP | 120 |
| 4 | Username | 120 |

#### Setting Tabel:
- Header: Background (0,137,123), Foreground White, Font Bold 13, Height 36
- Alternating rows: genap=White, ganjil=(245,245,245)
- Selection: Background (178,223,219), Foreground (33,33,33)
- Grid: horizontal only, color (224,224,224)

### Event:
- `tblKurir` → ListSelectionListener → isi form dari baris terpilih + load akun via controller
- `btnSimpanKurir` → validasi → `ctrl.insertWithAccount(kurir, username, password)` → refresh tabel → clear form
- `btnUbahKurir` → validasi selectedId → `ctrl.update(kurir)` + `ctrl.updateAccount(...)` → refresh → clear
- `btnHapusKurir` → konfirmasi → `ctrl.delete(kurir)` → refresh → clear
- `btnClearKurir` → kosongkan semua field, reset selectedId = -1, clearSelection tabel

---

## 5. PANEL PAKET (PanelPaket.java)

### Tipe: JPanel
- **Variable name**: pnlPaket
- **Background**: (250, 250, 250)
- **Layout**: BorderLayout(0, 12)
- **Border**: EmptyBorder(20, 20, 20, 20)

### Struktur:

```
┌──────────────────────────────────────────────────┐
│ NORTH: lblTitlePaket + Form Panel                │
│ ┌──────────────────────────────────────────────┐ │
│ │ Form (GridBagLayout 4 kolom)                 │ │
│ │ [Label][Field]  [Label][Field]               │ │
│ │ [Label][Field]  [Label][ComboBox]            │ │
│ │ [Label][Field]  [Label][Field]               │ │
│ │ [Label][Field]  [Label][ComboBox]            │ │
│ │ [Label][Combo]  [Label][lblTotal]            │ │
│ │ [Simpan][Ubah][Hapus][Clear]                 │ │
│ └──────────────────────────────────────────────┘ │
├──────────────────────────────────────────────────┤
│ CENTER: Table Panel                              │
│ ┌──────────────────────────────────────────────┐ │
│ │ JScrollPane → tblPaket                       │ │
│ └──────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────┘
```

### Panel Form (pnlFormPaket) — di dalam NORTH
- **Background**: White
- **Border**: LineBorder (224,224,224) + EmptyBorder(16, 16, 16, 16)
- **Layout**: GridBagLayout (4 kolom: label, field, label, field)

| No | Komponen | Variable Name | GridBag (gridx, gridy) | Properties |
|----|----------|---------------|------------------------|------------|
| 1 | JLabel | lblNoResi | (0, 0) | Text: "No Resi" |
| 2 | JTextField | txtNoResi | (1, 0) | PreferredSize: (0, 30) |
| 3 | JLabel | lblNamaBarang | (2, 0) | Text: "Nama Barang" |
| 4 | JTextField | txtNamaBarang | (3, 0) | PreferredSize: (0, 30) |
| 5 | JLabel | lblPengirim | (0, 1) | Text: "Pengirim" |
| 6 | JTextField | txtPengirim | (1, 1) | PreferredSize: (0, 30) |
| 7 | JLabel | lblJenisLayanan | (2, 1) | Text: "Jenis Layanan" |
| 8 | JComboBox | cmbJenisLayanan | (3, 1) | Items: "Reguler", "Express", "Cargo" |
| 9 | JLabel | lblPenerima | (0, 2) | Text: "Penerima" |
| 10 | JTextField | txtPenerima | (1, 2) | PreferredSize: (0, 30) |
| 11 | JLabel | lblBerat | (2, 2) | Text: "Berat (kg)" |
| 12 | JTextField | txtBerat | (3, 2) | PreferredSize: (0, 30) |
| 13 | JLabel | lblAlamat | (0, 3) | Text: "Alamat Tujuan" |
| 14 | JTextField | txtAlamat | (1, 3) | PreferredSize: (0, 30) |
| 15 | JLabel | lblKurir | (2, 3) | Text: "Kurir" |
| 16 | JComboBox | cmbKurir | (3, 3) | Items: diisi dari controller "ID - Nama" |
| 17 | JLabel | lblStatus | (0, 4) | Text: "Status" |
| 18 | JComboBox | cmbStatusPaket | (1, 4) | Items: "Diproses", "Sedang Dikirim", "Terkirim". Enabled: false (default) |
| 19 | JLabel | lblTotalLabel | (2, 4) | Text: "Total Biaya" |
| 20 | JLabel | lblTotalBiaya | (3, 4) | Text: "Rp 0", Font: Segoe UI Bold 14, Foreground: (0,105,92) |
| 21 | JButton | btnSimpanPaket | | Text: "Simpan", Background: (0,137,123), Foreground: White |
| 22 | JButton | btnUbahPaket | | Text: "Ubah", Background: (0,105,92), Foreground: White |
| 23 | JButton | btnHapusPaket | | Text: "Hapus", Background: (211,47,47), Foreground: White |
| 24 | JButton | btnClearPaket | | Text: "Clear" |

#### Button Layout:
- 4 tombol dalam JPanel FlowLayout(LEFT, 8, 0)
- Posisi: gridy=5, gridx=0, gridwidth=4

### Panel Tabel — CENTER

| Komponen | Variable Name | Properties |
|----------|---------------|------------|
| JTable | tblPaket | Sama setting seperti tblKurir |
| JScrollPane | spPaket | Border: LineBorder (224,224,224) |

#### Kolom Tabel tblPaket:
| # | Nama Kolom | Lebar (approx) |
|---|-----------|----------------|
| 0 | No Resi | 100 |
| 1 | Pengirim | 100 |
| 2 | Penerima | 100 |
| 3 | Alamat Tujuan | 150 |
| 4 | Nama Barang | 100 |
| 5 | Layanan | 70 |
| 6 | Berat (kg) | 60 |
| 7 | Total Biaya | 90 |
| 8 | Status | 90 |
| 9 | Kurir | 100 |

### Event:
- `tblPaket` → ListSelectionListener → isi semua field dari baris terpilih, enable cmbStatusPaket, set txtNoResi non-editable
- `cmbJenisLayanan` → ActionListener → hitung ongkir otomatis
- `txtBerat` → KeyListener (keyReleased) → hitung ongkir otomatis
- `btnSimpanPaket` → validasi → cek duplikat resi → `paketCtrl.insert(...)` → refresh → clear
- `btnUbahPaket` → validasi → `paketCtrl.update(...)` → refresh → clear
- `btnHapusPaket` → konfirmasi → `paketCtrl.delete(...)` → refresh → clear
- `btnClearPaket` → kosongkan semua, reset cmbStatusPaket disabled, txtNoResi editable

### Hitung Ongkir Otomatis:
```java
String jenis = (String) cmbJenisLayanan.getSelectedItem();
double berat = Double.parseDouble(txtBerat.getText().trim());
double total = paketCtrl.hitungOngkir(jenis, berat);
lblTotalBiaya.setText(String.format("Rp %,.0f", total));
```

---

## 6. FORM DASHBOARD KURIR (FormDashboardKurir.java)

### Tipe: JFrame
- **Variable name**: FormDashboardKurir
- **Size**: 900 x 550
- **Location**: Center screen
- **Layout**: BorderLayout

### Struktur:

```
┌──────────────────────────────────────────────┐
│ NORTH: Header (teal background)              │
│ [Dashboard Kurir]              [Logout]      │
├──────────────────────────────────────────────┤
│ CENTER: Content                              │
│                                              │
│ "Daftar Paket Anda"                         │
│ ┌──────────────────────────────────────────┐ │
│ │ JScrollPane → tblPaketKurir              │ │
│ │                                          │ │
│ └──────────────────────────────────────────┘ │
│                                              │
│ [Update Status:] [ComboBox] [Update Status]  │
└──────────────────────────────────────────────┘
```

### Panel Header (pnlHeaderKurir) — NORTH
- **Background**: (0, 137, 123)
- **Border**: EmptyBorder(14, 20, 14, 20)
- **Layout**: BorderLayout

| Komponen | Variable Name | Position | Properties |
|----------|---------------|----------|------------|
| JLabel | lblTitleKurir | WEST | Text: "Dashboard Kurir", Font: Segoe UI Bold 18, Foreground: White |
| JButton | btnLogoutKurir | EAST | Text: "Logout", Background: (0,105,92), Foreground: White, BorderPainted: false |

### Panel Content — CENTER
- **Background**: (250, 250, 250)
- **Border**: EmptyBorder(20, 20, 20, 20)
- **Layout**: BorderLayout(0, 12)

| Komponen | Variable Name | Position | Properties |
|----------|---------------|----------|------------|
| JLabel | lblDaftarPaket | NORTH | Text: "Daftar Paket Anda", Font: Segoe UI Bold 16 |
| JScrollPane + JTable | tblPaketKurir | CENTER | Sama setting tabel seperti di atas |
| JPanel (status) | pnlStatusUpdate | SOUTH | FlowLayout(LEFT, 10, 8) |

### Panel Status Update (pnlStatusUpdate):

| Komponen | Variable Name | Properties |
|----------|---------------|------------|
| JLabel | lblUpdateStatus | Text: "Update Status:", Font: Segoe UI Plain 14 |
| JComboBox | cmbStatusKurir | Items: "Diproses", "Sedang Dikirim", "Terkirim", PreferredSize: (160, 30) |
| JButton | btnUpdateStatus | Text: "Update Status", Background: (0,137,123), Foreground: White |

### Kolom Tabel tblPaketKurir:
Sama persis dengan tblPaket (10 kolom).

### Constructor:
- Menerima parameter `int idKurir` — simpan sebagai field
- Load tabel hanya paket milik kurir ini: `paketCtrl.loadTableByKurir(tblPaketKurir, idKurir)`

### Event:
- `tblPaketKurir` → ListSelectionListener → set cmbStatusKurir sesuai status baris terpilih
- `btnUpdateStatus` → validasi ada baris terpilih → `paketCtrl.updateStatusByKurir(noResi, newStatus, idKurir)` → refresh tabel
- `btnLogoutKurir` → konfirmasi → dispose() → new FormLogin().setVisible(true)

---

## CATATAN PENTING UNTUK GUI BUILDER

### Setting JTable di GUI Builder:
1. Drag JTable ke panel → otomatis dibungkus JScrollPane
2. Klik kanan tabel → Properties → model → kosongkan (akan diisi dari controller)
3. Set `selectionMode` = SINGLE_SELECTION
4. Set `rowHeight` = 30
5. Untuk styling header dan alternating row, lakukan di code (bukan di GUI Builder)

### Setting JComboBox:
1. Drag JComboBox → Properties → model → isi items sesuai kebutuhan
2. Untuk cmbKurir yang diisi dari database, biarkan kosong dan isi via code

### Setting CardLayout di FormMenuUtama:
1. Buat JPanel (pnlContent) di CENTER
2. Klik kanan pnlContent → Set Layout → CardLayout
3. Tambahkan panel-panel (pnlDashboard, pnlKurir, pnlPaket) ke dalamnya
4. Setiap panel diberi constraint name: "dashboard", "kurir", "paket"

### Tips:
- Gunakan **Absolute Layout** jika GridBagLayout terlalu rumit di GUI Builder
- Atau gunakan **Free Design** (default NetBeans) lalu adjust manual
- Untuk sidebar, buat JPanel terpisah dengan fixed width di WEST
- Semua tombol: set `focusPainted = false` dan `borderPainted = false` untuk tampilan clean
- Password field: gunakan JPasswordField bukan JTextField

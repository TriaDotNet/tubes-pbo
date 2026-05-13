package view;

import controller.KurirController;
import controller.PaketController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import model.Kurir;

public class FormPaket extends JFrame {

    private JTextField txtResi;
    private JTextField txtPengirim;
    private JTextField txtPenerima;
    private JTextArea txtAlamat;
    private JTextField txtNamaBarang;
    private JComboBox<String> cbLayanan;
    private JTextField txtBerat;
    private JButton btnHitung;
    private JLabel lblTotalBiaya;
    private JComboBox<String> cbStatus;
    private JComboBox<String> cbKurir;

    private JButton btnTambah;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersihkan;
    private JButton btnTutup;

    private JTable tblPaket;

    private List<Kurir> daftarKurir;

    private final PaketController paketCtrl = new PaketController();
    private final KurirController kurirCtrl = new KurirController();
    private final NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public FormPaket() {
        setTitle("Manajemen Paket");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1180, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Styles.BG);

        add(buildHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(18, 0));
        body.setBackground(Styles.BG);
        body.setBorder(BorderFactory.createEmptyBorder(18, 24, 24, 24));
        body.add(buildForm(), BorderLayout.WEST);
        body.add(buildTable(), BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        btnHitung.addActionListener(e -> doHitung());
        btnTambah.addActionListener(e -> doInsert());
        btnUbah.addActionListener(e -> doUpdate());
        btnHapus.addActionListener(e -> doDelete());
        btnBersihkan.addActionListener(e -> bersihkan());
        btnTutup.addActionListener(e -> dispose());

        tblPaket.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblPaket.getSelectedRow();
                if (row < 0) return;
                DefaultTableModel m = (DefaultTableModel) tblPaket.getModel();
                txtResi.setText(String.valueOf(m.getValueAt(row, 0)));
                txtResi.setEditable(false);
                txtPengirim.setText(String.valueOf(m.getValueAt(row, 1)));
                txtPenerima.setText(String.valueOf(m.getValueAt(row, 2)));
                txtAlamat.setText(String.valueOf(m.getValueAt(row, 3)));
                txtNamaBarang.setText(String.valueOf(m.getValueAt(row, 4)));
                cbLayanan.setSelectedItem(String.valueOf(m.getValueAt(row, 5)));
                txtBerat.setText(String.valueOf(m.getValueAt(row, 6)));
                double total = ((Number) m.getValueAt(row, 7)).doubleValue();
                lblTotalBiaya.setText(rupiah.format(total));
                cbStatus.setSelectedItem(String.valueOf(m.getValueAt(row, 8)));
                selectKurirByName(String.valueOf(m.getValueAt(row, 9)));
            }
        });

        loadKurirCombo();
        paketCtrl.loadTable(tblPaket);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Styles.HEADER_BG);
        header.setPreferredSize(new Dimension(0, 80));
        header.setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 28));

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        JLabel t = new JLabel("Manajemen Paket");
        t.setFont(new Font("Segoe UI", Font.BOLD, 20));
        t.setForeground(Color.WHITE);
        JLabel s = new JLabel("Input, hitung ongkir & kelola status pengiriman");
        s.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        s.setForeground(new Color(0xB4C5D8));
        box.add(t);
        box.add(Box.createVerticalStrut(4));
        box.add(s);
        header.add(box, BorderLayout.WEST);
        return header;
    }

    private JPanel buildForm() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Styles.CARD);
        card.setPreferredSize(new Dimension(380, 0));
        card.setBorder(Styles.card());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);

        txtResi = new JTextField();
        txtPengirim = new JTextField();
        txtPenerima = new JTextField();
        txtAlamat = new JTextArea(3, 20);
        txtAlamat.setLineWrap(true);
        txtAlamat.setWrapStyleWord(true);
        txtNamaBarang = new JTextField();
        cbLayanan = new JComboBox<>(new String[]{"Reguler", "Express", "Cargo"});
        txtBerat = new JTextField();
        btnHitung = new JButton("Hitung");
        cbStatus = new JComboBox<>(new String[]{"Diproses", "Sedang Dikirim", "Terkirim"});
        cbKurir = new JComboBox<>();

        for (JComponent c : new JComponent[]{txtResi, txtPengirim, txtPenerima, txtNamaBarang, txtBerat, cbLayanan, cbStatus, cbKurir}) {
            c.setPreferredSize(new Dimension(0, 32));
        }

        JLabel titleForm = new JLabel("Form Paket");
        titleForm.setFont(Styles.FONT_SECTION);
        titleForm.setForeground(Styles.TEXT);

        JLabel sub = new JLabel("Data pengiriman & perhitungan ongkir");
        sub.setFont(Styles.FONT_LABEL);
        sub.setForeground(Styles.TEXT_MUTED);

        int y = 0;
        gbc.gridy = y++; card.add(titleForm, gbc);
        gbc.gridy = y++; card.add(sub, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(14, 0, 2, 0); card.add(miniLabel("No Resi"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtResi, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Nama Pengirim"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtPengirim, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Nama Penerima"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtPenerima, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Alamat Tujuan"), gbc);
        JScrollPane spAlamat = new JScrollPane(txtAlamat);
        spAlamat.setPreferredSize(new Dimension(0, 68));
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(spAlamat, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Nama Barang"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtNamaBarang, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Jenis Layanan"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(cbLayanan, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Berat (kg)"), gbc);
        JPanel beratRow = new JPanel(new BorderLayout(6, 0));
        beratRow.setOpaque(false);
        beratRow.add(txtBerat, BorderLayout.CENTER);
        Styles.primary(btnHitung);
        btnHitung.setPreferredSize(new Dimension(82, 32));
        beratRow.add(btnHitung, BorderLayout.EAST);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(beratRow, gbc);

        JPanel totalBox = new JPanel(new BorderLayout());
        totalBox.setBackground(new Color(0xE8F5EE));
        totalBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xABD9BD), 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        JLabel totalCap = new JLabel("Total Biaya");
        totalCap.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        totalCap.setForeground(new Color(0x2C7A51));
        lblTotalBiaya = new JLabel(rupiah.format(0));
        lblTotalBiaya.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalBiaya.setForeground(new Color(0x1E6B44));
        lblTotalBiaya.setHorizontalAlignment(SwingConstants.RIGHT);
        totalBox.add(totalCap, BorderLayout.WEST);
        totalBox.add(lblTotalBiaya, BorderLayout.EAST);
        gbc.gridy = y++; gbc.insets = new Insets(6, 0, 6, 0); card.add(totalBox, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Status Paket"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(cbStatus, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Kurir"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(cbKurir, gbc);

        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah Data");
        btnHapus = new JButton("Hapus");
        btnBersihkan = new JButton("Bersihkan");
        btnTutup = new JButton("Tutup");
        Styles.primary(btnTambah);
        Styles.warning(btnUbah);
        Styles.danger(btnHapus);
        Styles.secondary(btnBersihkan);
        Styles.secondary(btnTutup);
        for (JButton b : new JButton[]{btnTambah, btnUbah, btnHapus, btnBersihkan, btnTutup}) {
            b.setPreferredSize(new Dimension(0, 34));
        }

        JPanel row1 = new JPanel(new GridBagLayout());
        row1.setOpaque(false);
        GridBagConstraints rg = new GridBagConstraints();
        rg.fill = GridBagConstraints.HORIZONTAL;
        rg.weightx = 1.0;
        rg.insets = new Insets(10, 0, 4, 4);
        rg.gridx = 0; row1.add(btnTambah, rg);
        rg.insets = new Insets(10, 4, 4, 0);
        rg.gridx = 1; row1.add(btnUbah, rg);

        JPanel row2 = new JPanel(new GridBagLayout());
        row2.setOpaque(false);
        rg.insets = new Insets(4, 0, 4, 4);
        rg.gridx = 0; row2.add(btnHapus, rg);
        rg.insets = new Insets(4, 4, 4, 0);
        rg.gridx = 1; row2.add(btnBersihkan, rg);

        gbc.gridy = y++; gbc.insets = new Insets(8, 0, 0, 0); card.add(row1, gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 0, 0); card.add(row2, gbc);
        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 0, 0); card.add(btnTutup, gbc);

        JScrollPane sp = new JScrollPane(card);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Styles.CARD);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.setPreferredSize(new Dimension(400, 0));

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(Styles.BG);
        wrap.setPreferredSize(new Dimension(400, 0));
        wrap.add(sp, BorderLayout.CENTER);
        return wrap;
    }

    private JLabel miniLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(Styles.TEXT_MUTED);
        return l;
    }

    private JPanel buildTable() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Styles.CARD);
        card.setBorder(BorderFactory.createLineBorder(Styles.BORDER, 1));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        top.setBackground(Styles.CARD);
        top.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Styles.BORDER),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        JPanel tb = new JPanel();
        tb.setOpaque(false);
        tb.setLayout(new BoxLayout(tb, BoxLayout.Y_AXIS));
        JLabel t = new JLabel("Daftar Paket");
        t.setFont(Styles.FONT_SECTION);
        t.setForeground(Styles.TEXT);
        JLabel s = new JLabel("Klik baris untuk memuat data ke form");
        s.setFont(Styles.FONT_LABEL);
        s.setForeground(Styles.TEXT_MUTED);
        tb.add(t);
        tb.add(s);
        top.add(tb);

        tblPaket = new JTable();
        Styles.styleTable(tblPaket);

        JScrollPane sp = new JScrollPane(tblPaket);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Styles.CARD);

        card.add(top, BorderLayout.NORTH);
        card.add(sp, BorderLayout.CENTER);
        return card;
    }

    private void loadKurirCombo() {
        daftarKurir = kurirCtrl.getAll();
        cbKurir.removeAllItems();
        for (Kurir k : daftarKurir) {
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
            double b = Double.parseDouble(txtBerat.getText().trim());
            if (b <= 0) {
                JOptionPane.showMessageDialog(this, "Berat harus lebih dari 0.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            return b;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Berat harus berupa angka.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private boolean validasi() {
        if (txtResi.getText().trim().isEmpty() || txtPengirim.getText().trim().isEmpty()
                || txtPenerima.getText().trim().isEmpty() || txtAlamat.getText().trim().isEmpty()
                || txtNamaBarang.getText().trim().isEmpty() || txtBerat.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (getSelectedKurirId() < 0) {
            JOptionPane.showMessageDialog(this, "Pilih kurir dulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void doHitung() {
        Double b = parseBerat();
        if (b == null) return;
        double total = paketCtrl.hitungOngkir((String) cbLayanan.getSelectedItem(), b);
        lblTotalBiaya.setText(rupiah.format(total));
    }

    private void doInsert() {
        if (!validasi()) return;
        Double b = parseBerat();
        if (b == null) return;
        String resi = txtResi.getText().trim();
        if (paketCtrl.isResiExist(resi)) {
            JOptionPane.showMessageDialog(this, "No Resi '" + resi + "' sudah terdaftar.",
                    "Duplikat", JOptionPane.WARNING_MESSAGE);
            return;
        }
        paketCtrl.insert(resi, txtPengirim.getText().trim(), txtPenerima.getText().trim(),
                txtAlamat.getText().trim(), txtNamaBarang.getText().trim(),
                (String) cbLayanan.getSelectedItem(), b, getSelectedKurirId());
        paketCtrl.loadTable(tblPaket);
        bersihkan();
    }

    private void doUpdate() {
        if (txtResi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel dulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validasi()) return;
        Double b = parseBerat();
        if (b == null) return;
        paketCtrl.update(txtResi.getText().trim(), txtPengirim.getText().trim(),
                txtPenerima.getText().trim(), txtAlamat.getText().trim(),
                txtNamaBarang.getText().trim(),
                (String) cbLayanan.getSelectedItem(), b,
                (String) cbStatus.getSelectedItem(), getSelectedKurirId());
        paketCtrl.loadTable(tblPaket);
        bersihkan();
    }

    private void doDelete() {
        if (txtResi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel dulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int c = JOptionPane.showConfirmDialog(this, "Yakin hapus paket ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (c != JOptionPane.YES_OPTION) return;
        paketCtrl.delete(txtResi.getText().trim());
        paketCtrl.loadTable(tblPaket);
        bersihkan();
    }

    private void bersihkan() {
        txtResi.setText("");
        txtResi.setEditable(true);
        txtPengirim.setText("");
        txtPenerima.setText("");
        txtAlamat.setText("");
        txtNamaBarang.setText("");
        cbLayanan.setSelectedIndex(0);
        txtBerat.setText("");
        lblTotalBiaya.setText(rupiah.format(0));
        cbStatus.setSelectedIndex(0);
        if (cbKurir.getItemCount() > 0) cbKurir.setSelectedIndex(0);
        tblPaket.clearSelection();
    }

    public static void main(String[] args) {
        Styles.init();
        java.awt.EventQueue.invokeLater(() -> new FormPaket().setVisible(true));
    }
}

package view;

import controller.KurirController;
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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Kurir;
import model.User;

public class FormKurir extends JFrame {

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtPlat;
    private JTextField txtHp;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnTambah;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersihkan;
    private JButton btnTutup;
    private JTable tblKurir;
    private JLabel lblHint;

    private final KurirController kurirCtrl = new KurirController();

    public FormKurir() {
        setTitle("Manajemen Kurir");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1080, 640);
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

        btnTambah.addActionListener(e -> doInsert());
        btnUbah.addActionListener(e -> doUpdate());
        btnHapus.addActionListener(e -> doDelete());
        btnBersihkan.addActionListener(e -> bersihkan());
        btnTutup.addActionListener(e -> dispose());

        tblKurir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblKurir.getSelectedRow();
                if (row < 0) return;
                DefaultTableModel m = (DefaultTableModel) tblKurir.getModel();
                txtId.setText(String.valueOf(m.getValueAt(row, 0)));
                txtNama.setText(String.valueOf(m.getValueAt(row, 1)));
                txtPlat.setText(String.valueOf(m.getValueAt(row, 2)));
                txtHp.setText(String.valueOf(m.getValueAt(row, 3)));
                int idKurir = Integer.parseInt(txtId.getText().trim());
                User akun = kurirCtrl.getAccountByKurir(idKurir);
                txtUsername.setText(akun != null ? akun.getUsername() : "");
                txtPassword.setText("");
                lblHint.setText(akun != null
                        ? "Akun login terdaftar. Kosongkan bila tidak ingin ubah."
                        : "Belum ada akun. Isi username & password.");
            }
        });

        kurirCtrl.loadTable(tblKurir);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Styles.HEADER_BG);
        header.setPreferredSize(new Dimension(0, 80));
        header.setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 28));

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        JLabel t = new JLabel("Manajemen Kurir");
        t.setFont(new Font("Segoe UI", Font.BOLD, 20));
        t.setForeground(Color.WHITE);
        JLabel s = new JLabel("Data kurir & akun login dashboard");
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
        card.setPreferredSize(new Dimension(340, 0));
        card.setBorder(Styles.card());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(0xF2F4F7));
        txtNama = new JTextField();
        txtPlat = new JTextField();
        txtHp = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        for (javax.swing.JComponent c : new javax.swing.JComponent[]{txtId, txtNama, txtPlat, txtHp, txtUsername, txtPassword}) {
            c.setPreferredSize(new Dimension(0, 32));
        }

        JLabel titleForm = new JLabel("Form Kurir");
        titleForm.setFont(Styles.FONT_SECTION);
        titleForm.setForeground(Styles.TEXT);

        JLabel sub = new JLabel("Isi data kurir & akun login");
        sub.setFont(Styles.FONT_LABEL);
        sub.setForeground(Styles.TEXT_MUTED);

        int y = 0;
        gbc.gridy = y++; card.add(titleForm, gbc);
        gbc.gridy = y++; card.add(sub, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(14, 0, 2, 0); card.add(miniLabel("ID Kurir"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtId, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Nama Kurir"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtNama, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("No Plat"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtPlat, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("No HP"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtHp, gbc);

        JLabel section = new JLabel("AKUN LOGIN");
        section.setFont(new Font("Segoe UI", Font.BOLD, 11));
        section.setForeground(Styles.TEXT_MUTED);
        gbc.gridy = y++; gbc.insets = new Insets(14, 0, 6, 0); card.add(section, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 2, 0); card.add(miniLabel("Username"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtUsername, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(miniLabel("Password"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtPassword, gbc);

        lblHint = new JLabel("Isi username & password untuk membuat akun.");
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblHint.setForeground(Styles.TEXT_MUTED);
        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 0, 0); card.add(lblHint, gbc);

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
        int bh = 34;
        for (JButton b : new JButton[]{btnTambah, btnUbah, btnHapus, btnBersihkan, btnTutup}) {
            b.setPreferredSize(new Dimension(0, bh));
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

        return card;
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
        JLabel t = new JLabel("Daftar Kurir");
        t.setFont(Styles.FONT_SECTION);
        t.setForeground(Styles.TEXT);
        JLabel s = new JLabel("Klik baris untuk mengedit atau menghapus");
        s.setFont(Styles.FONT_LABEL);
        s.setForeground(Styles.TEXT_MUTED);
        tb.add(t);
        tb.add(s);
        top.add(tb);

        tblKurir = new JTable();
        Styles.styleTable(tblKurir);

        JScrollPane sp = new JScrollPane(tblKurir);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Styles.CARD);

        card.add(top, BorderLayout.NORTH);
        card.add(sp, BorderLayout.CENTER);
        return card;
    }

    private boolean validasi() {
        if (txtNama.getText().trim().isEmpty() || txtPlat.getText().trim().isEmpty() || txtHp.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama, No Plat, dan No HP wajib diisi.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void doInsert() {
        if (!validasi()) return;
        if (txtUsername.getText().trim().isEmpty() || new String(txtPassword.getPassword()).trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password login wajib diisi.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Kurir k = new Kurir(txtNama.getText().trim(), txtPlat.getText().trim(), txtHp.getText().trim());
        boolean ok = kurirCtrl.insertWithAccount(k, txtUsername.getText().trim(),
                new String(txtPassword.getPassword()).trim());
        if (ok) {
            kurirCtrl.loadTable(tblKurir);
            bersihkan();
        }
    }

    private void doUpdate() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel dulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validasi()) return;
        int idKurir = Integer.parseInt(txtId.getText().trim());
        Kurir k = new Kurir(idKurir, txtNama.getText().trim(), txtPlat.getText().trim(), txtHp.getText().trim());
        kurirCtrl.update(k);
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        if (!username.isEmpty() && !password.isEmpty()) {
            kurirCtrl.updateAccount(idKurir, username, password);
        }
        kurirCtrl.loadTable(tblKurir);
        bersihkan();
    }

    private void doDelete() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel dulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int c = JOptionPane.showConfirmDialog(this,
                "Yakin hapus kurir ini? Akun login-nya juga akan dihapus.",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (c != JOptionPane.YES_OPTION) return;
        Kurir k = new Kurir();
        k.setId_kurir(Integer.parseInt(txtId.getText().trim()));
        kurirCtrl.delete(k);
        kurirCtrl.loadTable(tblKurir);
        bersihkan();
    }

    private void bersihkan() {
        txtId.setText("");
        txtNama.setText("");
        txtPlat.setText("");
        txtHp.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        tblKurir.clearSelection();
        lblHint.setText("Isi username & password untuk membuat akun.");
    }

    public static void main(String[] args) {
        Styles.init();
        java.awt.EventQueue.invokeLater(() -> new FormKurir().setVisible(true));
    }
}

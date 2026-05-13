package view;

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
import javax.swing.table.DefaultTableModel;
import model.User;

public class FormDashboardKurir extends JFrame {

    private JLabel lblInfoKurir;
    private JTextField txtResi;
    private JTextField txtPenerima;
    private JTextArea txtAlamat;
    private JTextField txtNamaBarang;
    private JTextField txtStatus;
    private JComboBox<String> cbStatus;
    private JButton btnUpdateStatus;
    private JButton btnLogout;
    private JTable tblPaket;
    private JLabel lblTotalPaket;
    private JLabel lblDiproses;
    private JLabel lblDikirim;
    private JLabel lblTerkirim;

    private User currentUser;
    private final PaketController paketCtrl = new PaketController();

    public FormDashboardKurir() {
        this(null);
    }

    public FormDashboardKurir(User user) {
        this.currentUser = user;
        setTitle("Dashboard Kurir");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Styles.BG);

        add(buildHeader(user), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 16));
        body.setBackground(Styles.BG);
        body.setBorder(BorderFactory.createEmptyBorder(18, 24, 24, 24));
        body.add(buildStatsRow(), BorderLayout.NORTH);

        JPanel split = new JPanel(new BorderLayout(18, 0));
        split.setOpaque(false);
        split.add(buildDetail(), BorderLayout.WEST);
        split.add(buildTable(), BorderLayout.CENTER);
        body.add(split, BorderLayout.CENTER);

        add(body, BorderLayout.CENTER);

        btnUpdateStatus.addActionListener(e -> doUpdateStatus());
        btnLogout.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                new FormLogin().setVisible(true);
                dispose();
            }
        });

        tblPaket.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblPaket.getSelectedRow();
                if (row < 0) return;
                DefaultTableModel m = (DefaultTableModel) tblPaket.getModel();
                txtResi.setText(String.valueOf(m.getValueAt(row, 0)));
                txtPenerima.setText(String.valueOf(m.getValueAt(row, 2)));
                txtAlamat.setText(String.valueOf(m.getValueAt(row, 3)));
                txtNamaBarang.setText(String.valueOf(m.getValueAt(row, 4)));
                txtStatus.setText(String.valueOf(m.getValueAt(row, 8)));
                cbStatus.setSelectedItem(String.valueOf(m.getValueAt(row, 8)));
            }
        });

        reload();
    }

    private JPanel buildHeader(User user) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Styles.HEADER_BG_KURIR);
        header.setPreferredSize(new Dimension(0, 90));
        header.setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 24));

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        JLabel t = new JLabel("Dashboard Kurir");
        t.setFont(new Font("Segoe UI", Font.BOLD, 22));
        t.setForeground(Color.WHITE);
        lblInfoKurir = new JLabel("Halo, " + (user != null ? user.getUsername() : "Kurir"));
        lblInfoKurir.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInfoKurir.setForeground(new Color(0xB5D4C1));
        box.add(t);
        box.add(Box.createVerticalStrut(4));
        box.add(lblInfoKurir);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        JLabel badge = new JLabel("  KURIR  ");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(Styles.HEADER_BG_KURIR);
        badge.setOpaque(true);
        badge.setBackground(new Color(0xE2F0E8));
        badge.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        btnLogout = new JButton("Logout");
        btnLogout.setPreferredSize(new Dimension(90, 32));
        btnLogout.setBackground(new Color(0x8B3A3A));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(Styles.FONT_BOLD);

        right.add(badge);
        right.add(btnLogout);

        header.add(box, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel buildStatsRow() {
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 12);

        lblTotalPaket = new JLabel("0");
        lblDiproses = new JLabel("0");
        lblDikirim = new JLabel("0");
        lblTerkirim = new JLabel("0");

        gbc.gridx = 0; row.add(statCard("Total Paket", lblTotalPaket, Styles.PRIMARY), gbc);
        gbc.gridx = 1; row.add(statCard("Diproses", lblDiproses, Styles.WARNING), gbc);
        gbc.gridx = 2; row.add(statCard("Sedang Dikirim", lblDikirim, new Color(0x0F9BCC)), gbc);
        gbc.gridx = 3; gbc.insets = new Insets(0, 0, 0, 0);
        row.add(statCard("Terkirim", lblTerkirim, Styles.SUCCESS), gbc);
        return row;
    }

    private JPanel statCard(String label, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(Styles.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Styles.BORDER, 1),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
        ));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel bar = new JPanel();
        bar.setBackground(accent);
        bar.setMaximumSize(new Dimension(36, 3));
        bar.setPreferredSize(new Dimension(36, 3));
        bar.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JLabel cap = new JLabel(label);
        cap.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cap.setForeground(Styles.TEXT_MUTED);
        cap.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(Styles.TEXT);
        valueLabel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        content.add(bar);
        content.add(Box.createVerticalStrut(10));
        content.add(cap);
        content.add(Box.createVerticalStrut(4));
        content.add(valueLabel);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildDetail() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Styles.CARD);
        card.setPreferredSize(new Dimension(360, 0));
        card.setBorder(Styles.card());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);

        txtResi = new JTextField();
        txtPenerima = new JTextField();
        txtAlamat = new JTextArea(3, 20);
        txtNamaBarang = new JTextField();
        txtStatus = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"Diproses", "Sedang Dikirim", "Terkirim"});

        txtResi.setEditable(false);
        txtPenerima.setEditable(false);
        txtAlamat.setEditable(false);
        txtAlamat.setLineWrap(true);
        txtAlamat.setWrapStyleWord(true);
        txtNamaBarang.setEditable(false);
        txtStatus.setEditable(false);

        Color readBg = new Color(0xF2F4F7);
        txtResi.setBackground(readBg);
        txtPenerima.setBackground(readBg);
        txtAlamat.setBackground(readBg);
        txtNamaBarang.setBackground(readBg);
        txtStatus.setBackground(readBg);

        for (JComponent c : new JComponent[]{txtResi, txtPenerima, txtNamaBarang, txtStatus, cbStatus}) {
            c.setPreferredSize(new Dimension(0, 32));
        }

        JLabel t = new JLabel("Detail Paket");
        t.setFont(Styles.FONT_SECTION);
        t.setForeground(Styles.TEXT);

        JLabel sub = new JLabel("Pilih paket dari tabel untuk update status");
        sub.setFont(Styles.FONT_LABEL);
        sub.setForeground(Styles.TEXT_MUTED);

        int y = 0;
        gbc.gridy = y++; card.add(t, gbc);
        gbc.gridy = y++; card.add(sub, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(14, 0, 2, 0); card.add(mini("No Resi"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtResi, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(mini("Penerima"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtPenerima, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(mini("Alamat"), gbc);
        JScrollPane sc = new JScrollPane(txtAlamat);
        sc.setPreferredSize(new Dimension(0, 68));
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(sc, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(mini("Nama Barang"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtNamaBarang, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(mini("Status Saat Ini"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(txtStatus, gbc);

        gbc.gridy = y++; gbc.insets = new Insets(4, 0, 2, 0); card.add(mini("Ubah Status Ke"), gbc);
        gbc.gridy = y++; gbc.insets = new Insets(0, 0, 6, 0); card.add(cbStatus, gbc);

        btnUpdateStatus = new JButton("Update Status");
        Styles.success(btnUpdateStatus);
        btnUpdateStatus.setPreferredSize(new Dimension(0, 38));

        gbc.gridy = y++; gbc.insets = new Insets(14, 0, 0, 0); card.add(btnUpdateStatus, gbc);

        return card;
    }

    private JLabel mini(String text) {
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
        JLabel t = new JLabel("Paket yang Ditugaskan");
        t.setFont(Styles.FONT_SECTION);
        t.setForeground(Styles.TEXT);
        JLabel s = new JLabel("Hanya paket yang di-assign ke Anda");
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

    private void reload() {
        if (currentUser == null || currentUser.getId_kurir() == null) {
            javax.swing.table.TableModel tm = tblPaket.getModel();
            if (tm instanceof DefaultTableModel) {
                ((DefaultTableModel) tm).setRowCount(0);
            }
            bersihkan();
            refreshStats();
            return;
        }
        paketCtrl.loadTableByKurir(tblPaket, currentUser.getId_kurir());
        refreshStats();
        bersihkan();
    }

    private void refreshStats() {
        javax.swing.table.TableModel tm = tblPaket.getModel();
        int total = tm.getRowCount();
        int dp = 0, sd = 0, tk = 0;
        for (int i = 0; i < total; i++) {
            String st = String.valueOf(tm.getValueAt(i, 8));
            if ("Diproses".equals(st)) dp++;
            else if ("Sedang Dikirim".equals(st)) sd++;
            else if ("Terkirim".equals(st)) tk++;
        }
        lblTotalPaket.setText(String.valueOf(total));
        lblDiproses.setText(String.valueOf(dp));
        lblDikirim.setText(String.valueOf(sd));
        lblTerkirim.setText(String.valueOf(tk));
    }

    private void doUpdateStatus() {
        if (currentUser == null || currentUser.getId_kurir() == null) {
            JOptionPane.showMessageDialog(this,
                    "Sesi kurir tidak valid. Silakan login ulang.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtResi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih paket dari tabel dulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String statusBaru = (String) cbStatus.getSelectedItem();
        paketCtrl.updateStatusByKurir(txtResi.getText().trim(), statusBaru,
                currentUser.getId_kurir());
        reload();
    }

    private void bersihkan() {
        txtResi.setText("");
        txtPenerima.setText("");
        txtAlamat.setText("");
        txtNamaBarang.setText("");
        txtStatus.setText("");
        cbStatus.setSelectedIndex(0);
        tblPaket.clearSelection();
    }

    public static void main(String[] args) {
        Styles.init();
        java.awt.EventQueue.invokeLater(() -> new FormDashboardKurir().setVisible(true));
    }
}

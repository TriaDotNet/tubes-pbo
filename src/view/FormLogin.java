package view;

import controller.LoginController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import model.User;

public class FormLogin extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnKeluar;

    public FormLogin() {
        setTitle("Login - Sistem Manajemen Ekspedisi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(780, 460);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Styles.BG);

        add(buildLeft(), BorderLayout.WEST);
        add(buildRight(), BorderLayout.CENTER);

        btnLogin.addActionListener(e -> doLogin());
        btnKeluar.addActionListener(e -> System.exit(0));
        getRootPane().setDefaultButton(btnLogin);
    }

    private JPanel buildLeft() {
        JPanel left = new JPanel();
        left.setBackground(Styles.HEADER_BG);
        left.setPreferredSize(new Dimension(320, 0));
        left.setLayout(new BorderLayout());
        left.setBorder(BorderFactory.createEmptyBorder(40, 36, 36, 36));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new javax.swing.BoxLayout(top, javax.swing.BoxLayout.Y_AXIS));

        JLabel brand = new JLabel("EKSPEDISI");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 26));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel("Sistem Manajemen Pengiriman");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tagline.setForeground(new Color(0xB4C5D8));
        tagline.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        top.add(brand);
        top.add(javax.swing.Box.createVerticalStrut(6));
        top.add(tagline);
        top.add(javax.swing.Box.createVerticalStrut(32));
        top.add(feat("Multi-role (Admin & Kurir)"));
        top.add(javax.swing.Box.createVerticalStrut(8));
        top.add(feat("Kelola paket & armada"));
        top.add(javax.swing.Box.createVerticalStrut(8));
        top.add(feat("Tracking status pengiriman"));

        JLabel footer = new JLabel("© 2026 Sistem Ekspedisi");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(new Color(0x8FA1B8));

        left.add(top, BorderLayout.NORTH);
        left.add(footer, BorderLayout.SOUTH);
        return left;
    }

    private JLabel feat(String text) {
        JLabel l = new JLabel("•  " + text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(new Color(0xD6E4F5));
        l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        return l;
    }

    private JPanel buildRight() {
        JPanel wrap = new JPanel(new GridBagLayout());
        wrap.setBackground(Styles.BG);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Styles.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Styles.BORDER, 1),
                BorderFactory.createEmptyBorder(28, 36, 28, 36)
        ));
        card.setPreferredSize(new Dimension(380, 340));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);

        JLabel title = new JLabel("Selamat Datang");
        title.setFont(Styles.FONT_TITLE);
        title.setForeground(Styles.TEXT);
        JLabel sub = new JLabel("Silakan masuk untuk melanjutkan");
        sub.setFont(Styles.FONT_LABEL);
        sub.setForeground(Styles.TEXT_MUTED);

        JLabel lblU = new JLabel("USERNAME");
        lblU.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblU.setForeground(Styles.TEXT_MUTED);
        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(0, 34));

        JLabel lblP = new JLabel("PASSWORD");
        lblP.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblP.setForeground(Styles.TEXT_MUTED);
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(0, 34));

        btnLogin = new JButton("Masuk");
        btnLogin.setPreferredSize(new Dimension(0, 38));
        Styles.primary(btnLogin);

        btnKeluar = new JButton("Keluar");
        btnKeluar.setPreferredSize(new Dimension(0, 34));
        Styles.secondary(btnKeluar);

        gbc.gridy = 0; card.add(title, gbc);
        gbc.gridy = 1; card.add(sub, gbc);
        gbc.gridy = 2; gbc.insets = new Insets(20, 0, 4, 0); card.add(lblU, gbc);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 8, 0); card.add(txtUsername, gbc);
        gbc.gridy = 4; gbc.insets = new Insets(4, 0, 4, 0); card.add(lblP, gbc);
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 12, 0); card.add(txtPassword, gbc);
        gbc.gridy = 6; gbc.insets = new Insets(12, 0, 4, 0); card.add(btnLogin, gbc);
        gbc.gridy = 7; gbc.insets = new Insets(0, 0, 0, 0); card.add(btnKeluar, gbc);

        GridBagConstraints g = new GridBagConstraints();
        wrap.add(card, g);
        return wrap;
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LoginController loginCtrl = new LoginController();
        User user = loginCtrl.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Username atau password salah.",
                    "Login Gagal", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Login berhasil sebagai " + user.getRole() + " (" + user.getUsername() + ")");

        if (user.isAdmin()) {
            new FormMenuUtama(user).setVisible(true);
            dispose();
        } else if (user.isKurir()) {
            if (user.getId_kurir() == null) {
                JOptionPane.showMessageDialog(this,
                        "Akun kurir belum ditautkan ke data kurir.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FormDashboardKurir(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Role tidak dikenali: " + user.getRole(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Styles.init();
        java.awt.EventQueue.invokeLater(() -> new FormLogin().setVisible(true));
    }
}

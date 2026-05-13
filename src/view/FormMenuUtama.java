package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.User;

public class FormMenuUtama extends JFrame {

    private JButton btnKurir;
    private JButton btnPaket;
    private JButton btnLogout;
    private User currentUser;

    public FormMenuUtama() {
        this(null);
    }

    public FormMenuUtama(User user) {
        this.currentUser = user;
        setTitle("Menu Utama - Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(860, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Styles.BG);

        add(buildHeader(user), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);
    }

    private JPanel buildHeader(User user) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Styles.HEADER_BG);
        header.setPreferredSize(new Dimension(0, 90));
        header.setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 24));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Dashboard Admin");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        JLabel sub = new JLabel("Kelola data kurir, paket & pengiriman");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(0xB4C5D8));
        text.add(title);
        text.add(Box.createVerticalStrut(4));
        text.add(sub);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        JLabel lblUser = new JLabel((user != null ? user.getUsername() : "Admin"));
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(Color.WHITE);

        JLabel badge = new JLabel("  ADMIN  ");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(Styles.HEADER_BG);
        badge.setOpaque(true);
        badge.setBackground(new Color(0xE8EEF5));
        badge.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        btnLogout = new JButton("Logout");
        btnLogout.setPreferredSize(new Dimension(90, 32));
        btnLogout.setBackground(new Color(0xB84545));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(Styles.FONT_BOLD);
        btnLogout.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                new FormLogin().setVisible(true);
                dispose();
            }
        });

        right.add(lblUser);
        right.add(badge);
        right.add(btnLogout);

        header.add(text, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel buildBody() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(Styles.BG);
        wrap.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JLabel section = new JLabel("Menu Utama");
        section.setFont(Styles.FONT_SECTION);
        section.setForeground(Styles.TEXT);
        section.setBorder(BorderFactory.createEmptyBorder(0, 4, 14, 0));

        JPanel grid = new JPanel(new GridLayout(1, 2, 20, 20));
        grid.setOpaque(false);

        btnKurir = new JButton("Buka");
        btnPaket = new JButton("Buka");
        Styles.primary(btnKurir);
        Styles.success(btnPaket);

        grid.add(makeCard("Manajemen Kurir",
                "Kelola data kurir dan akun login.",
                Styles.PRIMARY, btnKurir));
        grid.add(makeCard("Manajemen Paket",
                "Input, update & hapus data pengiriman.",
                Styles.SUCCESS, btnPaket));

        btnKurir.addActionListener(e -> new FormKurir().setVisible(true));
        btnPaket.addActionListener(e -> new FormPaket().setVisible(true));

        wrap.add(section, BorderLayout.NORTH);
        wrap.add(grid, BorderLayout.CENTER);
        return wrap;
    }

    private JPanel makeCard(String title, String desc, Color accent, JButton action) {
        JPanel card = new JPanel();
        card.setBackground(Styles.CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Styles.BORDER, 1),
                BorderFactory.createEmptyBorder(22, 26, 22, 26)
        ));

        JPanel bar = new JPanel();
        bar.setBackground(accent);
        bar.setMaximumSize(new Dimension(44, 4));
        bar.setPreferredSize(new Dimension(44, 4));
        bar.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 17));
        t.setForeground(Styles.TEXT);
        t.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JLabel d = new JLabel(desc);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        d.setForeground(Styles.TEXT_MUTED);
        d.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        action.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        action.setMaximumSize(new Dimension(110, 34));
        action.setPreferredSize(new Dimension(110, 34));

        card.add(bar);
        card.add(Box.createVerticalStrut(16));
        card.add(t);
        card.add(Box.createVerticalStrut(6));
        card.add(d);
        card.add(Box.createVerticalGlue());
        card.add(Box.createVerticalStrut(18));
        card.add(action);
        return card;
    }

    public static void main(String[] args) {
        Styles.init();
        java.awt.EventQueue.invokeLater(() -> new FormMenuUtama().setVisible(true));
    }
}

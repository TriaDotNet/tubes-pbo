package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class Styles {

    public static final Color BG = new Color(0xECF0F3);
    public static final Color CARD = Color.WHITE;
    public static final Color HEADER_BG = new Color(0x1E3A5F);
    public static final Color HEADER_BG_KURIR = new Color(0x1B5E3F);
    public static final Color PRIMARY = new Color(0x2563EB);
    public static final Color DANGER = new Color(0xC0392B);
    public static final Color WARNING = new Color(0xD68910);
    public static final Color SUCCESS = new Color(0x1E8449);
    public static final Color TEXT = new Color(0x2C3E50);
    public static final Color TEXT_MUTED = new Color(0x7F8C8D);
    public static final Color BORDER = new Color(0xBDC7D1);
    public static final Color ROW_ALT = new Color(0xF7F9FB);

    public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_HEADER_WHITE = new Font("Segoe UI", Font.PLAIN, 13);

    private Styles() {}

    public static void init() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignore) {
        }

        UIManager.put("control", BG);
        UIManager.put("nimbusBase", new Color(0x1E3A5F));
        UIManager.put("nimbusBlueGrey", new Color(0xB4BCC6));
        UIManager.put("nimbusLightBackground", CARD);
        UIManager.put("nimbusSelectionBackground", PRIMARY);
        UIManager.put("text", TEXT);

        UIManager.put("defaultFont", FONT);
        UIManager.put("Label.font", FONT);
        UIManager.put("Button.font", FONT_BOLD);
        UIManager.put("TextField.font", FONT);
        UIManager.put("PasswordField.font", FONT);
        UIManager.put("TextArea.font", FONT);
        UIManager.put("ComboBox.font", FONT);
        UIManager.put("Table.font", FONT);
        UIManager.put("TableHeader.font", FONT_BOLD);
        UIManager.put("TitledBorder.font", FONT_SECTION);
        UIManager.put("TitledBorder.titleColor", TEXT);
        UIManager.put("OptionPane.messageFont", FONT);
        UIManager.put("OptionPane.buttonFont", FONT_BOLD);
    }

    public static void primary(JButton b) {
        styleBtn(b, PRIMARY, Color.WHITE);
    }

    public static void danger(JButton b) {
        styleBtn(b, DANGER, Color.WHITE);
    }

    public static void warning(JButton b) {
        styleBtn(b, WARNING, Color.WHITE);
    }

    public static void success(JButton b) {
        styleBtn(b, SUCCESS, Color.WHITE);
    }

    public static void secondary(JButton b) {
        styleBtn(b, CARD, TEXT);
    }

    private static void styleBtn(JButton b, Color bg, Color fg) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(FONT_BOLD);
        b.setFocusPainted(false);
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public static javax.swing.border.Border card() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)
        );
    }

    public static javax.swing.border.Border section(String title) {
        javax.swing.border.TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                " " + title + " ",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                FONT_SECTION,
                TEXT
        );
        return BorderFactory.createCompoundBorder(
                tb,
                BorderFactory.createEmptyBorder(8, 10, 10, 10)
        );
    }

    public static void styleTable(JTable t) {
        t.setFont(FONT);
        t.setRowHeight(30);
        t.setGridColor(new Color(0xE1E7EE));
        t.setShowVerticalLines(false);
        t.setShowHorizontalLines(true);
        t.setSelectionBackground(new Color(0xD6E4FF));
        t.setSelectionForeground(TEXT);
        t.setFillsViewportHeight(true);
        t.setAutoCreateRowSorter(false);

        t.getTableHeader().setFont(FONT_BOLD);
        t.getTableHeader().setBackground(new Color(0xF0F3F7));
        t.getTableHeader().setForeground(TEXT);
        t.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 32));
        t.getTableHeader().setReorderingAllowed(false);

        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? CARD : ROW_ALT);
                    setForeground(TEXT);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                setHorizontalAlignment(value instanceof Number ? SwingConstants.RIGHT : SwingConstants.LEFT);
                return this;
            }
        });
    }
}

package dojomanager.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * Shared colours and helpers — soft pink & white theme.
 */
public final class UITheme {

    public static final Color PINK = new Color(232, 140, 170);        // soft pink
    public static final Color PINK_DARK = new Color(214, 110, 145);    // button pink
    public static final Color PINK_DEEP = new Color(196, 90, 130);     // header pink
    public static final Color CHARCOAL = new Color(70, 55, 62);        // soft dark text
    public static final Color BG = new Color(255, 248, 251);           // light pink-white
    public static final Color PANEL = Color.WHITE;
    public static final Color BORDER = new Color(245, 210, 225);       // pink border
    public static final Color MUTED = new Color(150, 120, 135);
    public static final Color TABLE_ALT = new Color(255, 240, 246);    // pale pink rows
    public static final Color SUCCESS = new Color(46, 125, 50);

    // Keep RED name as alias so older code still compiles if referenced
    public static final Color RED = PINK_DARK;
    public static final Color RED_DARK = PINK_DEEP;

    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font HEADING = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    private UITheme() {
    }

    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON);
        btn.setBackground(PINK_DARK);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    public static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON);
        btn.setBackground(Color.WHITE);
        btn.setForeground(CHARCOAL);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(9, 16, 9, 16)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    public static JButton dangerButton(String text) {
        JButton btn = secondaryButton(text);
        btn.setForeground(PINK_DEEP);
        return btn;
    }

    public static void styleTable(JTable table) {
        table.setFont(BODY);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(255, 220, 235));
        table.setSelectionForeground(CHARCOAL);
        table.setBackground(PANEL);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PINK_DEEP);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 36));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        tbl, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? PANEL : TABLE_ALT);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setHorizontalAlignment(SwingConstants.LEFT);
                return c;
            }
        });
    }

    public static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADING);
        label.setForeground(CHARCOAL);
        return label;
    }
}

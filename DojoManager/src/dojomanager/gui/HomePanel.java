package dojomanager.gui;

import dojomanager.data.DataManager;
import dojomanager.model.DojoInfo;
import dojomanager.model.KarateClass;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Welcome / dashboard screen.
 */
public class HomePanel extends JPanel {

    private final DataManager dataManager;
    private final JLabel dojoTitle;
    private final JLabel studentCountLabel;
    private final JLabel classCountLabel;
    private final JLabel instructorCountLabel;
    private final JLabel senseiLabel;
    private final JPanel todayList;
    private final JLabel todayTitle;

    public HomePanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout());
        setBackground(UITheme.BG);

        // Hero banner
        JPanel hero = new JPanel(new BorderLayout());
        hero.setBackground(UITheme.PINK_DEEP);
        hero.setBorder(BorderFactory.createEmptyBorder(36, 40, 36, 40));

        dojoTitle = new JLabel("Dojo Manager");
        dojoTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        dojoTitle.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Karate student & class management");
        subtitle.setFont(UITheme.BODY);
        subtitle.setForeground(new Color(255, 235, 242));

        senseiLabel = new JLabel(" ");
        senseiLabel.setFont(UITheme.SMALL);
        senseiLabel.setForeground(new Color(255, 220, 230));

        JPanel heroText = new JPanel();
        heroText.setOpaque(false);
        heroText.setLayout(new BoxLayout(heroText, BoxLayout.Y_AXIS));
        heroText.add(dojoTitle);
        heroText.add(Box.createVerticalStrut(6));
        heroText.add(subtitle);
        heroText.add(Box.createVerticalStrut(10));
        heroText.add(senseiLabel);

        hero.add(heroText, BorderLayout.WEST);
        hero.add(createHeroBadge(), BorderLayout.EAST);
        add(hero, BorderLayout.NORTH);

        // Stat cards
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel quick = UITheme.sectionTitle("Quick overview");
        quick.setAlignmentX(LEFT_ALIGNMENT);
        center.add(quick);
        center.add(Box.createVerticalStrut(16));

        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 0));
        cards.setOpaque(false);
        cards.setMaximumSize(new Dimension(900, 120));
        cards.setAlignmentX(LEFT_ALIGNMENT);

        studentCountLabel = new JLabel("0", SwingConstants.CENTER);
        classCountLabel = new JLabel("0", SwingConstants.CENTER);
        instructorCountLabel = new JLabel("0", SwingConstants.CENTER);
        cards.add(statCard("Students registered", studentCountLabel));
        cards.add(statCard("Classes scheduled", classCountLabel));
        cards.add(statCard("Instructors", instructorCountLabel));
        center.add(cards);

        center.add(Box.createVerticalStrut(28));

        // Today's classes
        todayTitle = UITheme.sectionTitle("Today's classes");
        todayTitle.setAlignmentX(LEFT_ALIGNMENT);
        center.add(todayTitle);
        center.add(Box.createVerticalStrut(12));

        todayList = new JPanel();
        todayList.setLayout(new BoxLayout(todayList, BoxLayout.Y_AXIS));
        todayList.setBackground(UITheme.PANEL);
        todayList.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
        ));
        todayList.setAlignmentX(LEFT_ALIGNMENT);
        todayList.setMaximumSize(new Dimension(900, 220));
        center.add(todayList);

        add(center, BorderLayout.CENTER);
        refreshStats();
    }

    /**
     * Builds the badge shown on the right of the hero banner. It tries to load
     * the black-belt image; if the image is missing it falls back to a simple
     * "DOJO" text badge so the program still works.
     *
     * @return a label containing the belt image, or a text badge as a fallback
     */
    private JLabel createHeroBadge() {
        File imgFile = new File("images/belt.png");
        if (imgFile.exists()) {
            ImageIcon raw = new ImageIcon(imgFile.getAbsolutePath());
            if (raw.getIconWidth() > 0) {
                Image scaled = raw.getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(scaled));
            }
        }
        JLabel badge = new JLabel("  DOJO  ", SwingConstants.CENTER);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setOpaque(true);
        badge.setBackground(Color.WHITE);
        badge.setForeground(UITheme.PINK_DEEP);
        badge.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return badge;
    }

    private JPanel statCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UITheme.PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                BorderFactory.createEmptyBorder(20, 22, 20, 22)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.SMALL);
        titleLabel.setForeground(UITheme.MUTED);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(UITheme.PINK_DEEP);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    public void refreshStats() {
        DojoInfo info = dataManager.getDojoInfo();
        dojoTitle.setText(info.getDojoName());
        senseiLabel.setText("Head instructor: " + info.getSenseiName());
        studentCountLabel.setText(String.valueOf(dataManager.getStudents().size()));
        classCountLabel.setText(String.valueOf(dataManager.getClasses().size()));
        instructorCountLabel.setText(String.valueOf(dataManager.getInstructorCount()));
        refreshTodayList();
    }

    private void refreshTodayList() {
        String today = dataManager.getTodayName();
        todayTitle.setText("Today's classes (" + today + ")");

        todayList.removeAll();
        List<KarateClass> todays = dataManager.getClassesForDay(today);
        if (todays.isEmpty()) {
            JLabel none = new JLabel("No classes scheduled for today.");
            none.setFont(UITheme.BODY);
            none.setForeground(UITheme.MUTED);
            none.setAlignmentX(LEFT_ALIGNMENT);
            todayList.add(none);
        } else {
            for (KarateClass c : todays) {
                JLabel row = new JLabel(c.getStartTime() + "   -   " + c.getClassName()
                        + "   (" + c.getInstructor() + ")");
                row.setFont(UITheme.BODY);
                row.setForeground(UITheme.CHARCOAL);
                row.setAlignmentX(LEFT_ALIGNMENT);
                row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
                todayList.add(row);
            }
        }
        todayList.revalidate();
        todayList.repaint();
    }
}

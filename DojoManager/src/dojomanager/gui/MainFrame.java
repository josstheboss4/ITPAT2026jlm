package dojomanager.gui;

import dojomanager.data.DataManager;
import dojomanager.util.ImageLoader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Main application window with branded header and tabs.
 */
public class MainFrame extends JFrame {

    private final DataManager dataManager;
    private final HomePanel homePanel;
    private final StudentPanel studentPanel;
    private final SchedulePanel schedulePanel;
    private final LocationPanel locationPanel;
    private final JLabel headerTitle;

    public MainFrame() {
        super("Dojo Manager");
        dataManager = new DataManager();

        ImageIcon windowIcon = ImageLoader.loadBeltIcon();
        if (windowIcon != null) {
            setIconImage(windowIcon.getImage());
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 620);
        setMinimumSize(new Dimension(820, 520));
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG);
        setLayout(new BorderLayout());

        // Top brand bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.PINK_DEEP);
        header.setBorder(BorderFactory.createEmptyBorder(14, 22, 14, 22));

        headerTitle = new JLabel(dataManager.getDojoInfo().getDojoName());
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerTitle.setForeground(Color.WHITE);
        header.add(headerTitle, BorderLayout.WEST);

        ImageIcon headerIcon = ImageLoader.loadBeltIcon(34, 34);
        if (headerIcon != null) {
            header.add(new JLabel(headerIcon), BorderLayout.EAST);
        }
        add(header, BorderLayout.NORTH);

        homePanel = new HomePanel(dataManager);
        studentPanel = new StudentPanel(dataManager, this);
        schedulePanel = new SchedulePanel(dataManager, this);
        locationPanel = new LocationPanel(dataManager);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(UITheme.BODY);
        tabs.setBackground(UITheme.BG);
        tabs.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        tabs.addTab("  Home  ", homePanel);
        tabs.addTab("  Students  ", studentPanel);
        tabs.addTab("  Class Schedule  ", schedulePanel);
        tabs.addTab("  Location  ", locationPanel);

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabs.getSelectedIndex();
                headerTitle.setText(dataManager.getDojoInfo().getDojoName());
                if (index == 0) {
                    homePanel.refreshStats();
                } else if (index == 1) {
                    studentPanel.refreshTable();
                } else if (index == 2) {
                    schedulePanel.refreshTable();
                } else if (index == 3) {
                    locationPanel.refreshDisplay();
                }
            }
        });

        add(tabs, BorderLayout.CENTER);
    }
}

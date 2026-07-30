package dojomanager.gui;

import dojomanager.data.DataManager;
import dojomanager.model.KarateClass;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Screen to view, add, edit (update) and delete class schedules.
 */
public class SchedulePanel extends JPanel {

    private static final String[] DAYS = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    private static final String[] TIMES = {
        "08:00", "09:00", "10:00", "11:00", "14:00", "15:00",
        "16:00", "17:00", "18:00", "19:00", "20:00"
    };

    private final DataManager dataManager;
    private final JFrame parentFrame;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField searchField;

    public SchedulePanel(DataManager dataManager, JFrame parentFrame) {
        this.dataManager = dataManager;
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout(12, 12));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));

        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        top.add(UITheme.sectionTitle("Class Schedule"), BorderLayout.WEST);

        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        searchRow.setOpaque(false);
        searchField = new JTextField(16);
        searchField.setFont(UITheme.BODY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        JButton searchBtn = UITheme.secondaryButton("Search");
        JButton clearBtn = UITheme.secondaryButton("Clear");
        searchBtn.addActionListener(e -> refreshTable());
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            refreshTable();
        });
        searchField.addActionListener(e -> refreshTable());
        searchRow.add(new JLabel("Find:"));
        searchRow.add(searchField);
        searchRow.add(searchBtn);
        searchRow.add(clearBtn);
        top.add(searchRow, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        String[] columns = {"ID", "Class Name", "Day", "Time", "Instructor", "Belt Level"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        UITheme.styleTable(table);

        // Double-click a row to edit that class
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelected();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));
        scroll.getViewport().setBackground(UITheme.PANEL);
        add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        JButton addBtn = UITheme.primaryButton("Add Class");
        JButton editBtn = UITheme.secondaryButton("Edit / Update Class");
        JButton deleteBtn = UITheme.dangerButton("Delete Class");
        JButton exportBtn = UITheme.secondaryButton("Export Timetable");
        JButton refreshBtn = UITheme.secondaryButton("Refresh");

        addBtn.addActionListener(e -> openClassDialog(null));
        editBtn.addActionListener(e -> editSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
        exportBtn.addActionListener(e -> exportTimetable());
        refreshBtn.addActionListener(e -> refreshTable());

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);
        buttons.add(exportBtn);
        buttons.add(refreshBtn);
        add(buttons, BorderLayout.SOUTH);

        refreshTable();
    }

    public void refreshTable() {
        String query = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (KarateClass c : dataManager.getClasses()) {
            if (!query.isEmpty()
                    && !c.getClassName().toLowerCase().contains(query)
                    && !c.getDayOfWeek().toLowerCase().contains(query)
                    && !c.getInstructor().toLowerCase().contains(query)) {
                continue;
            }
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getClassName(),
                c.getDayOfWeek(),
                c.getStartTime(),
                c.getInstructor(),
                c.getBeltLevel()
            });
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a class to update.");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        KarateClass karateClass = dataManager.findClassById(id);
        if (karateClass != null) {
            openClassDialog(karateClass);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a class to delete.");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        String name = (String) table.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete class \"" + name + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            dataManager.deleteClass(id);
            refreshTable();
        }
    }

    private void exportTimetable() {
        File report = dataManager.exportTimetable();
        if (report == null) {
            JOptionPane.showMessageDialog(this,
                    "Sorry, the timetable could not be saved.",
                    "Export failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int open = JOptionPane.showConfirmDialog(this,
                "Timetable saved to:\n" + report.getAbsolutePath()
                + "\n\nOpen it now?",
                "Export complete", JOptionPane.YES_NO_OPTION);
        if (open == JOptionPane.YES_OPTION) {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(report);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Saved, but could not open the file automatically.");
            }
        }
    }

    private void openClassDialog(KarateClass existing) {
        boolean editing = existing != null;
        JDialog dialog = new JDialog(parentFrame, editing ? "Update Class" : "Add Class", true);
        dialog.setSize(440, 360);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.getContentPane().setBackground(UITheme.BG);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 12));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JTextField nameField = new JTextField(editing ? existing.getClassName() : "");
        JComboBox<String> dayBox = new JComboBox<>(new DefaultComboBoxModel<>(DAYS));
        JComboBox<String> timeBox = new JComboBox<>(new DefaultComboBoxModel<>(TIMES));
        JTextField instructorField = new JTextField(editing ? existing.getInstructor() : "");
        JTextField beltField = new JTextField(editing ? existing.getBeltLevel() : "All belts");

        if (editing) {
            dayBox.setSelectedItem(existing.getDayOfWeek());
            timeBox.setSelectedItem(existing.getStartTime());
        }

        form.add(new JLabel("Class Name:"));
        form.add(nameField);
        form.add(new JLabel("Day:"));
        form.add(dayBox);
        form.add(new JLabel("Start Time:"));
        form.add(timeBox);
        form.add(new JLabel("Instructor:"));
        form.add(instructorField);
        form.add(new JLabel("Belt Level:"));
        form.add(beltField);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        bottom.setOpaque(false);
        JButton saveBtn = UITheme.primaryButton(editing ? "Update Class" : "Save Class");
        JButton cancelBtn = UITheme.secondaryButton("Cancel");

        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String day = (String) dayBox.getSelectedItem();
            String time = (String) timeBox.getSelectedItem();
            String instructor = instructorField.getText().trim();
            String belt = beltField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Class name is required.");
                return;
            }
            if (instructor.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Instructor is required.");
                return;
            }

            int currentId = editing ? existing.getId() : 0;
            if (dataManager.hasClassClash(currentId, day, time, instructor)) {
                JOptionPane.showMessageDialog(dialog,
                        instructor + " already has a class on " + day + " at " + time
                        + ".\nPlease choose a different day, time or instructor.",
                        "Class clash", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (editing) {
                existing.setClassName(name);
                existing.setDayOfWeek(day);
                existing.setStartTime(time);
                existing.setInstructor(instructor);
                existing.setBeltLevel(belt);
                dataManager.updateClass(existing);
            } else {
                dataManager.addClass(new KarateClass(0, name, day, time, instructor, belt));
            }
            refreshTable();
            dialog.dispose();
        });

        cancelBtn.addActionListener(e -> dialog.dispose());
        bottom.add(cancelBtn);
        bottom.add(saveBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(bottom, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}

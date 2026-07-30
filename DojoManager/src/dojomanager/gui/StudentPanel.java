package dojomanager.gui;

import dojomanager.data.DataManager;
import dojomanager.model.Student;
import dojomanager.util.Validation;
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
 * Screen to view, add, edit and delete students.
 */
public class StudentPanel extends JPanel {

    private static final String[] BELT_RANKS = {
        "White Belt", "Yellow Belt", "Orange Belt", "Green Belt",
        "Blue Belt", "Brown Belt", "Black Belt"
    };

    private final DataManager dataManager;
    private final JFrame parentFrame;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField searchField;

    public StudentPanel(DataManager dataManager, JFrame parentFrame) {
        this.dataManager = dataManager;
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout(12, 12));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));

        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        top.add(UITheme.sectionTitle("Student Management"), BorderLayout.WEST);

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

        String[] columns = {"ID", "Name", "Age", "Belt Rank", "Phone", "Email"};
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

        // Double-click a row to edit that student
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
        JButton addBtn = UITheme.primaryButton("Add Student");
        JButton editBtn = UITheme.secondaryButton("Edit Student");
        JButton deleteBtn = UITheme.dangerButton("Delete Student");
        JButton exportBtn = UITheme.secondaryButton("Export List");
        JButton refreshBtn = UITheme.secondaryButton("Refresh");

        addBtn.addActionListener(e -> openStudentDialog(null));
        editBtn.addActionListener(e -> editSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
        exportBtn.addActionListener(e -> exportList());
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
        for (Student s : dataManager.getStudents()) {
            if (!query.isEmpty() && !s.getName().toLowerCase().contains(query)
                    && !s.getBeltRank().toLowerCase().contains(query)) {
                continue;
            }
            tableModel.addRow(new Object[]{
                s.getId(),
                s.getName(),
                s.getAge(),
                s.getBeltRank(),
                s.getPhone(),
                s.getEmail()
            });
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        Student student = dataManager.findStudentById(id);
        if (student != null) {
            openStudentDialog(student);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        String name = (String) table.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete student \"" + name + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            dataManager.deleteStudent(id);
            refreshTable();
        }
    }

    private void exportList() {
        File report = dataManager.exportStudentList();
        if (report == null) {
            JOptionPane.showMessageDialog(this,
                    "Sorry, the student list could not be saved.",
                    "Export failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int open = JOptionPane.showConfirmDialog(this,
                "Student list saved to:\n" + report.getAbsolutePath()
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

    private void openStudentDialog(Student existing) {
        boolean editing = existing != null;
        JDialog dialog = new JDialog(parentFrame, editing ? "Edit Student" : "Add Student", true);
        dialog.setSize(420, 360);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.getContentPane().setBackground(UITheme.BG);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 12));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JTextField nameField = new JTextField(editing ? existing.getName() : "");
        JTextField ageField = new JTextField(editing ? String.valueOf(existing.getAge()) : "");
        JComboBox<String> beltBox = new JComboBox<>(new DefaultComboBoxModel<>(BELT_RANKS));
        if (editing) {
            beltBox.setSelectedItem(existing.getBeltRank());
        }
        JTextField phoneField = new JTextField(editing ? existing.getPhone() : "");
        JTextField emailField = new JTextField(editing ? existing.getEmail() : "");

        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Age:"));
        form.add(ageField);
        form.add(new JLabel("Belt Rank:"));
        form.add(beltBox);
        form.add(new JLabel("Phone:"));
        form.add(phoneField);
        form.add(new JLabel("Email:"));
        form.add(emailField);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        bottom.setOpaque(false);
        JButton saveBtn = UITheme.primaryButton("Save");
        JButton cancelBtn = UITheme.secondaryButton("Cancel");

        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String belt = (String) beltBox.getSelectedItem();

            if (!Validation.isValidName(name)) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a valid name (letters and spaces only).");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Age must be a whole number.");
                return;
            }
            if (!Validation.isValidAge(age)) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a realistic age (between 4 and 100).");
                return;
            }

            if (!Validation.isValidPhone(phone)) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a valid phone number (10 to 13 digits).");
                return;
            }

            if (!Validation.isValidEmail(email)) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a valid email address, e.g. name@email.com");
                return;
            }

            if (editing) {
                existing.setName(name);
                existing.setAge(age);
                existing.setBeltRank(belt);
                existing.setPhone(phone);
                existing.setEmail(email);
                dataManager.updateStudent(existing);
            } else {
                dataManager.addStudent(new Student(0, name, age, belt, phone, email));
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

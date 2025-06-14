package com.manager.GUI;

import com.manager.dao.StudentDao;
import com.manager.university.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class StudentManagementApp extends JFrame {

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private JTextField idField, firstNameField, lastNameField, emailField, majorField, graduationEndField;
    private StudentDao studentDao;

    public StudentManagementApp() {
        studentDao = new StudentDao();
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        refreshTableData();
    }

    private void initUI() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email", "Major", "Graduation Date"}, 0);
        studentTable = new JTable(tableModel);
        studentTable.setAutoCreateRowSorter(true);
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelectedRow();
            }
        });
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        idField = new JTextField();
        idField.setEditable(false);
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        majorField = new JTextField();
        graduationEndField = new JTextField();
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Major:"));
        formPanel.add(majorField);
        formPanel.add(new JLabel("Graduation Date (YYYY-MM-DD):"));
        formPanel.add(graduationEndField);
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add New");
        updateButton = new JButton("Update Selected");
        deleteButton = new JButton("Delete Selected");
        clearButton = new JButton("Clear Form");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(formPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearForm());
    }

    private void refreshTableData() {
        tableModel.setRowCount(0);
        try {
            List<Student> students = studentDao.getAll();
            for (Student student : students) {
                tableModel.addRow(new Object[]{
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getMajor(),
                        student.getGraduationEnd()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data from DB: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            firstNameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            lastNameField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            emailField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            majorField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            graduationEndField.setText(tableModel.getValueAt(selectedRow, 5) != null ? tableModel.getValueAt(selectedRow, 5).toString() : "");
        }
    }

    private void addStudent() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            if (firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "First name and last name are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(emailField.getText());
            student.setMajor(majorField.getText());
            student.setGraduationEnd(LocalDate.parse(graduationEndField.getText()));

            studentDao.add(student);

            refreshTableData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Format Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error during add operation: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unknown error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Student student = new Student();
            student.setId(Integer.parseInt(idField.getText()));
            student.setFirstName(firstNameField.getText());
            student.setLastName(lastNameField.getText());
            student.setEmail(emailField.getText());
            student.setMajor(majorField.getText());
            student.setGraduationEnd(LocalDate.parse(graduationEndField.getText()));

            studentDao.update(student);
            refreshTableData();
            JOptionPane.showMessageDialog(this, "Student data updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error during update operation: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred during update: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) { return; }
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                studentDao.delete(id);
                refreshTableData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error during delete operation: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        majorField.setText("");
        graduationEndField.setText("");
        studentTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            com.manager.server.DatabaseConnector.initializeDatabase();
            StudentManagementApp app = new StudentManagementApp();
            app.setVisible(true);
        });
    }
}

package com.manager.GUI;

import com.manager.dao.LecturerDao;
import com.manager.university.Lecturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class LecturerManagementApp extends JFrame {

    private JTable lecturerTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private JTextField idField, firstNameField, lastNameField, emailField, positionField, rankField;
    private LecturerDao lecturerDao;

    public LecturerManagementApp() {
        lecturerDao = new LecturerDao();
        setTitle("Lecturer Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        refreshTableData();
    }

    private void initUI() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email", "Position", "Academic Rank"}, 0);
        lecturerTable = new JTable(tableModel);
        lecturerTable.setAutoCreateRowSorter(true);

        lecturerTable.getSelectionModel().addListSelectionListener(e -> {
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
        positionField = new JTextField();
        rankField = new JTextField();

        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);
        formPanel.add(new JLabel("Academic Rank:"));
        formPanel.add(rankField);

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
        add(new JScrollPane(lecturerTable), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addLecturer());
        updateButton.addActionListener(e -> updateLecturer());
        deleteButton.addActionListener(e -> deleteLecturer());
        clearButton.addActionListener(e -> clearForm());
    }

    private void refreshTableData() {
        tableModel.setRowCount(0);
        try {
            List<Lecturer> lecturers = lecturerDao.getAll();
            for (Lecturer lecturer : lecturers) {
                tableModel.addRow(new Object[]{
                        lecturer.getId(),
                        lecturer.getFirstName(),
                        lecturer.getLastName(),
                        lecturer.getEmail(),
                        lecturer.getPosition(),
                        lecturer.getAcademicRank()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data from database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = lecturerTable.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            firstNameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            lastNameField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            emailField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            positionField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            rankField.setText(tableModel.getValueAt(selectedRow, 5).toString());
        }
    }

    private void addLecturer() {
        try {
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First Name and Last Name are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Lecturer lecturer = new Lecturer();
            lecturer.setFirstName(firstNameField.getText());
            lecturer.setLastName(lastNameField.getText());
            lecturer.setEmail(emailField.getText());
            lecturer.setPosition(positionField.getText());
            lecturer.setAcademicRank(rankField.getText());

            lecturerDao.add(lecturer);
            refreshTableData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Lecturer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error on add: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateLecturer() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer to update.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Lecturer lecturer = new Lecturer();
            lecturer.setId(Integer.parseInt(idField.getText()));
            lecturer.setFirstName(firstNameField.getText());
            lecturer.setLastName(lastNameField.getText());
            lecturer.setEmail(emailField.getText());
            lecturer.setPosition(positionField.getText());
            lecturer.setAcademicRank(rankField.getText());

            lecturerDao.update(lecturer);
            refreshTableData();
            JOptionPane.showMessageDialog(this, "Lecturer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error on update: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteLecturer() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer to delete.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this lecturer?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                lecturerDao.delete(Integer.parseInt(idField.getText()));
                refreshTableData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Lecturer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error on delete: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        positionField.setText("");
        rankField.setText("");
        lecturerTable.clearSelection();
    }
}
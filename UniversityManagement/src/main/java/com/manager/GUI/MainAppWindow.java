package com.manager.GUI;

import com.manager.server.DatabaseConnector;
import javax.swing.*;
import java.awt.*;

public class MainAppWindow extends JFrame {

    public MainAppWindow() {
        setTitle("University Management System - Main Menu");
        setSize(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton manageStudentsBtn = new JButton("Manage Students");
        manageStudentsBtn.setFont(new Font("Arial", Font.BOLD, 14));
        manageStudentsBtn.addActionListener(e -> {
            StudentManagementApp studentApp = new StudentManagementApp();
            studentApp.setVisible(true);
        });

        JButton manageLecturersBtn = new JButton("Manage Lecturers");
        manageLecturersBtn.setFont(new Font("Arial", Font.BOLD, 14));
        manageLecturersBtn.addActionListener(e -> {
            LecturerManagementApp lecturerApp = new LecturerManagementApp();
            lecturerApp.setVisible(true);
        });

        mainPanel.add(manageStudentsBtn);
        mainPanel.add(manageLecturersBtn);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnector.initializeDatabase();
            MainAppWindow mainApp = new MainAppWindow();
            mainApp.setVisible(true);
        });
    }
}
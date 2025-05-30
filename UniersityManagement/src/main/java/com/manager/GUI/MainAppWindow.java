package com.manager.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainAppWindow extends JFrame {

    private JTextField inputField;
    private JButton clickButton;
    private JLabel outputLabel;

    public MainAppWindow() {
        setTitle("qqqqq");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inputField = new JTextField();
        clickButton = new JButton("buttonn");
        outputLabel = new JLabel("clck");

        clickButton.addActionListener(this::handleButtonClick);

        setLayout(new BorderLayout(10, 10));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.add(inputField);
        centerPanel.add(clickButton);

        add(centerPanel, BorderLayout.CENTER);
        add(outputLabel, BorderLayout.SOUTH);
    }

    private void handleButtonClick(ActionEvent e) {
        String text = inputField.getText().trim();
        if(text.isEmpty())
            outputLabel.setText("empty field");
        else
            outputLabel.setText("your output: " + text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainAppWindow().setVisible(true));
    }
}
package com.project.views;

import com.project.controllers.Controller;
import com.project.models.NetworkRule;
import com.project.views.RuleView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditView extends JPanel {
    private final JTextField nameField, portField, typeField, appNameField, userNameField, ipAddressField, actionField, interfaceField, directionField, commentField;
    private final JButton createButton, cancelButton;
    private final Controller controller;

    public EditView(Controller controller) {
        this.controller = controller;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(11, 2, 5, 5));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Port:"));
        portField = new JTextField("--");
        formPanel.add(portField);

        formPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        formPanel.add(typeField);

        formPanel.add(new JLabel("Application Name:"));
        appNameField = new JTextField("--");
        formPanel.add(appNameField);

        formPanel.add(new JLabel("User Name:"));
        userNameField = new JTextField("--");
        formPanel.add(userNameField);

        formPanel.add(new JLabel("IP Address:"));
        ipAddressField = new JTextField();
        formPanel.add(ipAddressField);

        formPanel.add(new JLabel("Action:"));
        actionField = new JTextField();
        formPanel.add(actionField);

        formPanel.add(new JLabel("Network Interface:"));
        interfaceField = new JTextField();
        formPanel.add(interfaceField);

        formPanel.add(new JLabel("Direction:"));
        directionField = new JTextField();
        formPanel.add(directionField);

        formPanel.add(new JLabel("Comment:"));
        commentField = new JTextField();
        formPanel.add(commentField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");

        createButton.addActionListener(e -> {
            // Check if required fields are empty
            if (nameField.getText().isEmpty() || directionField.getText().isEmpty() ||
                    typeField.getText().isEmpty() || ipAddressField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "You must fill all the required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int result = JOptionPane.showConfirmDialog(this, "You are about to create a new firewall rule, are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    NetworkRule rule = getNetworkRule();
                    controller.createRule(rule);
                    controller.next(new RuleView(controller), this);
                }
            }
        });


        cancelButton.addActionListener(e -> {
            controller.next(new RuleView(controller), this);
        });

        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private NetworkRule getNetworkRule() {
        NetworkRule rule = new NetworkRule();
        rule.setName(nameField.getText());
        rule.setPort(portField.getText());
        rule.setType(typeField.getText());
        rule.setApplicationName(appNameField.getText());
        rule.setUserName(userNameField.getText());
        rule.setIpAddress(ipAddressField.getText());
        rule.setAction(actionField.getText());
        rule.setNetworkInterface(interfaceField.getText());
        rule.setDirection(directionField.getText());
        rule.setComment(commentField.getText());
        return rule;
    }
}

package com.project.controllers;

import com.project.FirewallApp;
import com.project.models.NetworkRule;
import com.project.views.EditView;
import com.project.views.RuleView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final FirewallApp firewallApp;
    private RuleView ruleView;
    private EditView editView;
    private final CardLayout layout;

    public Controller(FirewallApp firewallApp) {
        this.firewallApp = firewallApp;
        layout = new CardLayout();
    }

    public void next(JPanel newView, JPanel currentView) {
        firewallApp.getContentPane().remove(currentView);
        firewallApp.getContentPane().add(newView);
        layout.next(firewallApp.getContentPane());
    }

    public void deleteRule(String number, String chain) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sudo", "iptables", "-D", chain, number);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void modifyRule(NetworkRule rule) {
        List<String> command = new ArrayList<>();
        System.out.println(rule.toString());
        command.add("sudo");
        command.add("iptables");
        command.add("-R");
        command.add(rule.getName());
        command.add(rule.getNumber());
        command.add("-s");
        command.add(rule.getIpAddress());
        command.add("-p");
        command.add(rule.getType());
        command.add("--dport");
        command.add(rule.getPort());
        command.add("-j");
        command.add(rule.getAction());
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createRule(NetworkRule rule) {
        //boolean created = false;
        try {
            List<String> command = new ArrayList<>();
            command.add("sudo");
            command.add("iptables");
            command.add("-A");
            command.add(rule.getName());

            if (!rule.getType().isBlank()) {
                command.add("-p");
                command.add(rule.getType());
            }

            if (!rule.getPort().isBlank()) {
                command.add("--dport");
                command.add(rule.getPort());
            }

            command.add("-s");
            command.add(rule.getIpAddress());

            if (!rule.getAction().isBlank()) {
                command.add("-j");
                command.add(rule.getAction());
            }

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            //
        }
    }

    public void setRuleView(RuleView ruleView) {
        this.ruleView = ruleView;
    }

    public void setEditView(EditView editView) {
        this.editView = editView;
    }

    public RuleView getRuleView() {
        return ruleView;
    }

    public EditView getEditView() {
        return editView;
    }

    public CardLayout getLayout() {
        return layout;
    }
}

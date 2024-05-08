package com.project.controllers;

import com.project.FirewallApp;
import com.project.models.NetworkRule;
import com.project.views.EditView;
import com.project.views.RuleView;

import javax.swing.*;
import java.awt.*;

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

    public boolean createRule(NetworkRule rule) {
        boolean created = false;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sudo", "iptables", "-A", rule.getDirection(), "-p", rule.getType(), "--dport", rule.getPort(), "-s", rule.getIpAddress(), "-m", "comment", "--comment", rule.getComment(), "-j", rule.getDirection());
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            //
        }
        return created;
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

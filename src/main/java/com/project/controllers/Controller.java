package com.project.controllers;

import com.project.FirewallApp;
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

    public void next(JPanel newView) {
        System.out.println(firewallApp.getLayout());
        firewallApp.add(newView);
        layout.next(firewallApp);
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

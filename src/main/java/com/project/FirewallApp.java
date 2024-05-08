package com.project;

import com.project.controllers.Controller;
import com.project.utils.LogLib;
import com.project.views.EditView;
import com.project.views.RuleView;

import javax.swing.*;

import java.awt.*;

import static com.project.utils.Constants.LAUNCH_SIZE;
import static com.project.utils.Constants.TAG_FIREWALL_APP;

public class FirewallApp extends JFrame {
    private final Controller controller;
    private final JPanel contentPane;

    public FirewallApp() {
        super();
        controller = new Controller(this);
        contentPane = new JPanel();
        boolean initialized = initialize();

        if (!initialized) {
            dispose();
            return;
        }
        setVisible(true);
        LogLib.logInfo(TAG_FIREWALL_APP, FirewallApp.class, "Successfully initialized.", "FirewallApp constructor");
    }

    /**
     * Initializer method.
     * Setups the frame configurations and components.
     *
     * @return True if successfully initialized, false otherwise.
     */
    private boolean initialize() {
        LogLib.logInfo(TAG_FIREWALL_APP, FirewallApp.class, "Initializing Firewall App...", "initialize");
        setTitle("Firewall App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(LAUNCH_SIZE);
        contentPane.setLayout(controller.getLayout());

        return initializeComponents();
    }

    /**
     * Method to initialize frame components.
     *
     * @return True if successfully initialized, false otherwise.
     */
    private boolean initializeComponents() {
        LogLib.logInfo(TAG_FIREWALL_APP, FirewallApp.class, "Initializing components...", "initializeComponents");
        boolean initialized = false;
        controller.setRuleView(new RuleView(controller));
        RuleView ruleView = controller.getRuleView();
        if (ruleView.isInitialized()) {
            contentPane.add(ruleView);
            initialized = true;
        }
        setContentPane(contentPane);
        return initialized;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}

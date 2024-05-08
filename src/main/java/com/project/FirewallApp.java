package com.project;

import com.project.utils.LogLib;

import javax.swing.*;

import static com.project.utils.Constants.LAUNCH_SIZE;
import static com.project.utils.Constants.TAG_FIREWALL_APP;

public class FirewallApp extends JFrame {

    public FirewallApp() {
        super();
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

        return initializeComponents();
    }

    /**
     * Method to initialize frame components.
     *
     * @return True if successfully initialized, false otherwise.
     */
    private boolean initializeComponents() {
        LogLib.logInfo(TAG_FIREWALL_APP, FirewallApp.class, "Initializing components...", "initializeComponents");
        return true;
    }




}

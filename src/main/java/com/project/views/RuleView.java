package com.project.views;

import com.project.controllers.Controller;
import com.project.models.NetworkRule;
import com.project.utils.LogLib;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

import static com.project.utils.Constants.*;
import static com.project.utils.NetworkRuleParser.getRules;

public class RuleView extends JPanel {
    private JLabel title;
    private JTable ruleTable;
    private JButton newRuleButton, modifyRuleButton, deleteRuleButton;
    private final boolean isInitialized;
    private final Controller controller;

    public RuleView(Controller controller) {
        super(new BorderLayout());

        boolean initialized = initialize();
        this.controller = controller;
        if (!initialized) {
            LogLib.logWarn(TAG_RULE_VIEW, RuleView.class, "Unable to initialize RuleView.", "RuleView");
        }
        populateTableColumns();
        setVisible(true);
        isInitialized = initialized;
    }

    /**
     * Initializes the View.
     *
     * @return True if successful, false otherwise.
     */
    private boolean initialize() {
        boolean initialized = false;
        initialized = initializeComponents();

        if (initialized) {
            initializeLayout();
        }
        return initialized;
    }

    /**
     * Initializes the View layout.
     */
    private void initializeLayout() {
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(ruleTable), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for buttons
        buttonPanel.add(newRuleButton);
        buttonPanel.add(modifyRuleButton);
        buttonPanel.add(deleteRuleButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Initializes the View components.
     *
     * @return True if all components where successfully initialized, false otherwise.
     */
    private boolean initializeComponents() {
        LogLib.logInfo(TAG_RULE_VIEW, this.getClass(), "Initializing RuleView components...", "initializeComponents");
        initializeTitle();
        boolean initialized = initializeTable();
        initialized &= initializeNewRuleButton();
        initialized &= initializeModifyRuleButton();
        initialized &= initializeDeleteRuleButton();
        return initialized;
    }

    /**
     * Initializes the view title.
     */
    private void initializeTitle() {
        Font titleFont = new Font(Font.MONOSPACED, Font.BOLD, RULE_VIEW_TITLE_SIZE);

        title = new JLabel(RULE_VIEW_TITLE);
        title.setFont(titleFont);
    }

    /**
     * Initializes the rule table.
     *
     * @return True if successful, false otherwise.
     */
    private boolean initializeTable() {
        ruleTable = new JTable();
        return initializeTableColumns();
    }

    /**
     * Initializes the rule table columns.
     *
     * @return True if successful, false otherwise.
     */
    private boolean initializeTableColumns() {
        boolean initialized = false;
        try {
            DefaultTableModel tableModel = new DefaultTableModel();
            ruleTable.setModel(tableModel);
            List<String> columnNames = List.of("Name", "Port", "Type", "Application", "User", "Description", "Ip", "Action", "Interface", "IN/OUT");
            for (String columnName : columnNames) {
                tableModel.addColumn(columnName);
            }
            initialized = true;
        } catch (Exception e) {
            LogLib.logError(TAG_RULE_VIEW, this.getClass(), "Error while adding table columns", "initializeTableColumns", e);
        }
        return initialized;
    }

    /**
     * Initializes the new rule button.
     *
     * @return True if successful, false otherwise.
     */
    private boolean initializeNewRuleButton() {
        boolean initialized = false;
        try {
            newRuleButton = new JButton(NEW_RULE_BUTTON_LABEL);
            newRuleButton.addActionListener(e -> {
                LogLib.logInfo(TAG_RULE_VIEW, this.getClass(), "Moving to the edit view", "initializeNewRuleButton");
                controller.next(new EditView(controller), this);
            });
            initialized = true;
        } catch (Exception e) {
            LogLib.logError(TAG_RULE_VIEW, this.getClass(), "Error while adding new rule button", "initializeNewRuleButton", e);
        }
        return initialized;
    }

    /**
     * Initializes the modify rule button.
     *
     * @return True if successful, false otherwise.
     */
    private boolean initializeModifyRuleButton() {
        boolean initialized = false;
        try {
            modifyRuleButton = new JButton(MODIFY_RULE_BUTTON_LABEL);
            modifyRuleButton.addActionListener(e -> {
                //TODO implement button logic;
            });
            initialized = true;
        } catch (Exception e) {
            LogLib.logError(TAG_RULE_VIEW, this.getClass(), "Error while adding modify rule button", "initializeModifyRuleButton", e);
        }
        return initialized;
    }

    /**
     * Initializes the delete rule button.
     *
     * @return True if successful, false otherwise.
     */
    private boolean initializeDeleteRuleButton() {
        boolean initialized = false;
        try {
            deleteRuleButton = new JButton(DELETE_RULE_BUTTON_LABEL);
            deleteRuleButton.addActionListener(e -> {
                //TODO implement button logic;
            });
            initialized = true;
        } catch (Exception e) {
            LogLib.logError(TAG_RULE_VIEW, this.getClass(), "Error while adding delete rule button", "initializeDeleteRuleButton", e);
        }
        return initialized;
    }

    private void populateTableColumns() {
        List<NetworkRule> networkRules = getRules();
        DefaultTableModel tableModel = (DefaultTableModel) ruleTable.getModel();
        for (NetworkRule networkRule : networkRules) {
            tableModel.addRow(new Object[]{networkRule.getName(), networkRule.getPort(), networkRule.getType(), networkRule.getApplicationName(), networkRule.getUserName(), networkRule.getComment(), networkRule.getIpAddress(), networkRule.getAction(), networkRule.getNetworkInterface(), networkRule.getDirection()});
        }
    }


    public boolean isInitialized() {
        return isInitialized;
    }
}

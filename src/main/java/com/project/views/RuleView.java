package com.project.views;

import com.project.controllers.Controller;
import com.project.models.NetworkRule;
import com.project.utils.LogLib;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.TableView;

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
                int selectedRow = ruleTable.getSelectedRow();
                TableModel model = ruleTable.getModel();
                NetworkRule rule = new NetworkRule();

                String name = (String) model.getValueAt(selectedRow, 0);
                String number = name.split("\\s+")[1];
                String chain = name.split("\\s+")[0];
                System.out.println(selectedRow);
                if (selectedRow != -1) {
                    rule.setName(chain);
                    rule.setNumber(number);
                    rule.setNetworkInterface(model.getValueAt(selectedRow, 8).toString());
                    rule.setAction(model.getValueAt(selectedRow, 7).toString());
                    rule.setPort(model.getValueAt(selectedRow, 1).toString());
                    rule.setDirection(model.getValueAt(selectedRow, 9).toString());
                    rule.setType(model.getValueAt(selectedRow, 2).toString());
                    rule.setIpAddress(model.getValueAt(selectedRow, 6).toString());

                    rule.setComment(model.getValueAt(selectedRow, 5).toString());

                    System.out.println("hello");
                    controller.next(new EditView(controller, rule), this);
                }

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
                int selectedRow = ruleTable.getSelectedRow();
                if (selectedRow != -1) {
                    TableModel tableModel = ruleTable.getModel();
                    String rule = (String) tableModel.getValueAt(selectedRow, 0);
                    String number = rule.split("\\s+")[1];
                    String chain = rule.split("\\s+")[0];

                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure to delete the rule?", "Confirmation", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        controller.deleteRule(number, chain);

                        if (tableModel instanceof DefaultTableModel) {
                            ((DefaultTableModel) tableModel).removeRow(selectedRow);
                        }

                    }
                }
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

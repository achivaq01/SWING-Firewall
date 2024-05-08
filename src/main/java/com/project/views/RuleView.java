package com.project.views;

import com.project.utils.LogLib;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.util.List;

import static com.project.utils.Constants.*;

public class RuleView extends JPanel{
    private JLabel title;
    private JTable ruleTable;
    private JButton newRuleButton, modifyRuleButton, deleteRuleButton;

    public RuleView() {
        super();
    }

    private boolean initialize() {
        return initializeComponents();
    }

    private boolean initializeComponents() {
        boolean initialized = initializeTable();
        initialized &= initializeNewRuleButton();
        initialized &= initializeModifyRuleButton();
        initialized &= initializeDeleteRuleButton();
        return initialized;
    }


    private void initializeTitle() {
        title = new JLabel(RULE_VIEW_TITLE);
        title.setSize(RULE_VIEW_TITLE_DIMENSIONS);
    }

    private boolean initializeTable() {
        ruleTable = new JTable();
        ruleTable.setColumnModel(new DefaultTableColumnModel());
        return initializeTableColumns();
    }

    private boolean initializeTableColumns() {
        boolean initialized = false;
        try {
            List<String> columnNames = List.of("Name", "Port", "Type", "Application", "User", "Description", "Ip",
                    "Action", "Interface", "IN/OUT");

            for (String columnName : columnNames) {
                TableColumn column = new TableColumn();
                column.setHeaderValue(columnName);
                ruleTable.addColumn(column);
            }
            initialized = true;
        } catch (Exception e) {
            LogLib.logError(TAG_RULE_VIEW, this.getClass(), "Error while adding table columns", "initializeTableColumns", e);
        }
        return initialized;
    }

    private boolean initializeNewRuleButton() {
        boolean initialized = false;
        try {
            newRuleButton = new JButton(NEW_RULE_BUTTON_LABEL);
            newRuleButton.addActionListener(e -> {
                //TODO implement button logic;
            });
            initialized = true;
        } catch (Exception e) {
            LogLib.logError(TAG_RULE_VIEW, this.getClass(), "Error while adding new rule button", "initializeNewRuleButton", e);
        }
        return initialized;
    }

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
}

package br.com.sysdesc.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import br.com.sysdesc.components.listeners.ButtonCollumnActionListener;

public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private JButton renderButton;
    private JButton editButton;
    private String text = "...";
    protected EventListenerList buttonListener = new EventListenerList();

    public ButtonColumn(JTable table, int column) {
        super();

        this.table = table;

        initComponents(column);
    }

    private void initComponents(int column) {
        renderButton = new JButton();

        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        } else if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        renderButton.setText(text);
        return renderButton;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        editButton.setText(text);

        return editButton;
    }

    public Object getCellEditorValue() {

        return text;
    }

    public void actionPerformed(ActionEvent e) {

        fireEditingStopped();

        fireButtonListener(table.getSelectedRow());

        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
    }

    public void addButtonListener(ButtonCollumnActionListener actionListener) {

        buttonListener.add(ButtonCollumnActionListener.class, actionListener);
    }

    private void fireButtonListener(int row) {

        Object[] listeners = buttonListener.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2) {

            if (listeners[i] == ButtonCollumnActionListener.class) {

                ((ButtonCollumnActionListener) listeners[i + 1]).buttonClicked(row);
            }
        }
    }
}

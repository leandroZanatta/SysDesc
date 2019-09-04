package br.com.sysdesc.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class JmoneyFieldColumn extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor, ActionListener {

	private static final long serialVersionUID = 1L;

	private JMoneyField renderButton;
	private JMoneyField editButton;

	public JmoneyFieldColumn(JTable table, int column) {
		this(table, column, 2);
	}

	public JmoneyFieldColumn(JTable table, int column, int casasDecimais) {
		super();

		renderButton = new JMoneyField(casasDecimais);

		editButton = new JMoneyField(casasDecimais);

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (hasFocus) {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("TextField.background"));
			renderButton.setBorder(table.getBorder());
		} else if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
			renderButton.setBorder(table.getBorder());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("TextField.background"));
			renderButton.setBorder(table.getBorder());
		}

		renderButton.setValue((BigDecimal) value);

		return renderButton;
	}

	public void setCasasDecimais(int casasDecimais) {

		renderButton.setCasasDecimais(casasDecimais);
		editButton.setCasasDecimais(casasDecimais);

	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		editButton.setValue((BigDecimal) value);

		return editButton;
	}

	public Object getCellEditorValue() {

		return editButton.getValue();
	}

	public void actionPerformed(ActionEvent e) {

		fireEditingStopped();
	}

}

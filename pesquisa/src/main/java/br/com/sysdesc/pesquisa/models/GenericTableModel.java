package br.com.sysdesc.pesquisa.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import br.com.sysdesc.repository.model.PesquisaCampo;

public class GenericTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<T> rows = new ArrayList<>();
	private List<String> configuracoesPesquisa;

	public GenericTableModel(List<PesquisaCampo> campos) {

		configuracoesPesquisa = campos.stream().map(PesquisaCampo::getDescricao).collect(Collectors.toList());
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		return null;
	}

	@Override
	public int getColumnCount() {
		return configuracoesPesquisa.size();
	}

	@Override
	public String getColumnName(int column) {
		return configuracoesPesquisa.get(column);
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		return String.class;
	}

	@Override
	public boolean isCellEditable(int row, int column) {

		return Boolean.FALSE;
	}

	public T getRow(int selectedRow) {
		return rows.get(selectedRow);
	}

	public void remove(int selectedRow) {
		rows.remove(selectedRow);
		fireTableDataChanged();
	}

	public void removeAll() {
		rows = new ArrayList<>();
		fireTableDataChanged();
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
		fireTableDataChanged();
	}

	public void addRow(T objeto) {
		this.rows.add(objeto);

		fireTableDataChanged();

	}

	public void addRows(List<T> objeto) {
		this.rows.addAll(objeto);

		fireTableDataChanged();

	}
}
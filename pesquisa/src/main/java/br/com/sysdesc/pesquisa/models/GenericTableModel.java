package br.com.sysdesc.pesquisa.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.sysdesc.repository.model.PesquisaCampo;

public class GenericTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<T> rows = new ArrayList<>();
	private List<PesquisaCampo> configuracoesPesquisa;

	public GenericTableModel(List<PesquisaCampo> campos) {

		campos.sort(Comparator.comparing(PesquisaCampo::getFlagOrdem));

		configuracoesPesquisa = campos;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		T value = rows.get(rowIndex);

		return getValue(value, configuracoesPesquisa.get(columnIndex));
	}

	private Object getValue(T value, PesquisaCampo pesquisaCampo) {

		Object valorcampo = parseValue(value, pesquisaCampo.getCampo().split("\\."));

		if (valorcampo == null) {
			return "";
		}

		return valorcampo.toString();
	}

	private <K> Object parseValue(K value, String[] camposObjeto) {

		if (camposObjeto.length == 1) {

			return parseObject(value, camposObjeto[0]);
		}

		Object objeto = parseObject(value, camposObjeto[0]);

		if (objeto == null) {

			return null;
		}

		Object subObject = parseValue(objeto, Arrays.copyOfRange(camposObjeto, 1, camposObjeto.length));

		if (subObject == null) {

			return null;
		}

		return subObject;
	}

	private <K> Object parseObject(K value, String campoObjeto) {

		Field field;

		try {
			field = value.getClass().getDeclaredField(campoObjeto);

			field.setAccessible(Boolean.TRUE);

			return field.get(value);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {

			return null;
		}

	}

	@Override
	public int getColumnCount() {
		return configuracoesPesquisa.size();
	}

	@Override
	public String getColumnName(int column) {

		return configuracoesPesquisa.get(column).getDescricao();
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
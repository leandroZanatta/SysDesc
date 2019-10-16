package br.com.sysdesc.tablemodels;

import static br.com.sysdesc.util.resources.Resources.TBLMODULOS_CODIGO;
import static br.com.sysdesc.util.resources.Resources.TBLMODULOS_CONFIGURACAO;
import static br.com.sysdesc.util.resources.Resources.TBLMODULOS_DESACOPLADO;
import static br.com.sysdesc.util.resources.Resources.TBLMODULOS_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.TBLMODULOS_GERENCIADOR;
import static br.com.sysdesc.util.resources.Resources.TBLMODULOS_IP;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrameTable;
import br.com.sysdesc.repository.model.Modulo;
import br.com.sysdesc.repository.model.ModuloPDV;

public class ModulosTableModel extends AbstractInternalFrameTable {

	private static final String SIM = "S";
	private static final long serialVersionUID = 1L;
	private List<String> colunas = new ArrayList<>();
	private List<ModuloPDV> rows = new ArrayList<>();

	public ModulosTableModel(List<ModuloPDV> rows) {
		colunas.add(translate(TBLMODULOS_CODIGO));
		colunas.add(translate(TBLMODULOS_IP));
		colunas.add(translate(TBLMODULOS_DESCRICAO));
		colunas.add(translate(TBLMODULOS_DESACOPLADO));
		colunas.add(translate(TBLMODULOS_GERENCIADOR));
		colunas.add(translate(TBLMODULOS_CONFIGURACAO));

		this.rows = rows;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		ModuloPDV modulo = rows.get(rowIndex);

		switch (columnIndex) {

		case 0:
			return getPropertie(modulo, ModuloPDV::getIdModuloPDV);
		case 1:
			return getPropertie(modulo, ModuloPDV::getIpModulo);
		case 2:
			return getPropertie(getPropertie(modulo, ModuloPDV::getModulo), Modulo::getDescricao);
		case 3:
			return getPropertie(getPropertie(modulo, ModuloPDV::getModulo), Modulo::getDesacoplado);
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		ModuloPDV moduloPDV = getRow(rowIndex);

		if (columnIndex == 1 && SIM.equals(moduloPDV.getModulo().getDesacoplado())) {

			moduloPDV.setIpModulo(aValue.toString());
		}

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		if (columnIndex == 0) {
			return Long.class;
		}

		if (columnIndex != 4 && columnIndex != 5) {
			return String.class;
		}

		return JPanel.class;

	}

	@Override
	public boolean isCellEditable(int row, int column) {

		if (column == 4 || column == 5) {
			return true;
		}

		ModuloPDV moduloPDV = getRow(row);

		return column == 1 && SIM.equals(moduloPDV.getModulo().getDesacoplado());
	}

	@Override
	public int getColumnCount() {
		return colunas.size();
	}

	@Override
	public String getColumnName(int column) {
		return colunas.get(column);
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	public ModuloPDV getRow(int selectedRow) {
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

	public List<ModuloPDV> getRows() {
		return rows;
	}

	public void setRows(List<ModuloPDV> rows) {
		this.rows = rows;
		fireTableDataChanged();
	}

	@Override
	public void clear() {

		this.rows.clear();

		fireTableDataChanged();
	}

	@Override
	public void setEnabled(Boolean enabled) {
		return;
	}

	private <K, P> P getPropertie(K objeto, Function<K, P> function) {

		if (objeto == null) {

			return null;
		}

		return function.apply(objeto);
	}
}

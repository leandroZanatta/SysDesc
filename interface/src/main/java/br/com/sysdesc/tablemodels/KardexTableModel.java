package br.com.sysdesc.tablemodels;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import br.com.sysdesc.components.AbstractInternalFrameTable;
import br.com.sysdesc.util.classes.DateUtil;
import br.com.sysdesc.util.vo.KardexVO;

public class KardexTableModel extends AbstractInternalFrameTable {

	private static final long serialVersionUID = 1L;
	private List<String> colunas = new ArrayList<>();
	private List<KardexVO> rows = new ArrayList<>();
	private NumberFormat formatQuantidadeFormat = NumberFormat.getNumberInstance();
	private NumberFormat formatMonetario = NumberFormat.getNumberInstance();

	public KardexTableModel() {
		colunas.add("Data");
		colunas.add("Operação");
		colunas.add("Quantidade");
		colunas.add("Valor");
		colunas.add("Custo unitário");
		colunas.add("Quantidade Total");
		colunas.add("Saldo Total");

		formatQuantidadeFormat.setMaximumFractionDigits(3);
		formatQuantidadeFormat.setMinimumFractionDigits(3);

		formatMonetario.setMaximumFractionDigits(2);
		formatMonetario.setMinimumFractionDigits(2);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		KardexVO kardexvo = rows.get(rowIndex);

		switch (columnIndex) {

		case 0:
			return DateUtil.format(DateUtil.FORMATO_DD_MM_YYYY_HH_MM_SS, kardexvo.getDataMovimento());
		case 1:
			return kardexvo.getTipoOperacao();
		case 2:
			return formatQuantidadeFormat.format(kardexvo.getQuantidade());
		case 3:
			return formatMonetario.format(kardexvo.getValorOperacao());
		case 4:
			return formatQuantidadeFormat.format(kardexvo.getCustoUnitario());
		case 5:
			return formatQuantidadeFormat.format(kardexvo.getQuantidadeTotal());
		case 6:
			return formatMonetario.format(kardexvo.getSaldoTotal());
		default:
			throw new NotImplementedException("Coluna não configurada para exibir");
		}

	}

	@Override
	public boolean isCellEditable(int row, int column) {

		return false;
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

	public KardexVO getRow(int selectedRow) {

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

	public List<KardexVO> getRows() {
		return rows;
	}

	public void setRows(List<KardexVO> rows) {
		this.rows = rows;
		fireTableDataChanged();
	}

	@Override
	public void clear() {

		this.rows.clear();

	}

	@Override
	public void setEnabled(Boolean enabled) {

	}
}

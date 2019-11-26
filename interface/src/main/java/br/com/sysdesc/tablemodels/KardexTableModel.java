package br.com.sysdesc.tablemodels;

import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_BARRAS;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_CODIGO;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_PRODUTO;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_QUANTIDADE;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_UNIDADE;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_VALOR_TOTAL;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_VALOR_UNITARIO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.util.ArrayList;
import java.util.List;

import br.com.sysdesc.components.AbstractInternalFrameTable;
import br.com.sysdesc.util.resources.Resources;
import br.com.sysdesc.util.vo.KardexVO;

public class KardexTableModel extends AbstractInternalFrameTable {

	private static final long serialVersionUID = 1L;
	private List<String> configuracoesPesquisa = new ArrayList<>();
	private List<KardexVO> rows = new ArrayList<>();

	public KardexTableModel() {
		configuracoesPesquisa.add(translate(Resources.TBLENTRADA_CODIGO));
		configuracoesPesquisa.add(translate(TBLENTRADA_CODIGO));
		configuracoesPesquisa.add(translate(TBLENTRADA_BARRAS));
		configuracoesPesquisa.add(translate(TBLENTRADA_PRODUTO));
		configuracoesPesquisa.add(translate(TBLENTRADA_UNIDADE));
		configuracoesPesquisa.add(translate(TBLENTRADA_QUANTIDADE));
		configuracoesPesquisa.add(translate(TBLENTRADA_VALOR_UNITARIO));
		configuracoesPesquisa.add(translate(TBLENTRADA_VALOR_TOTAL));
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		return rows.get(rowIndex);

	}

	@Override
	public boolean isCellEditable(int row, int column) {

		return false;
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

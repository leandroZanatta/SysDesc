package br.com.sysdesc.tablemodels;

import static br.com.sysdesc.util.classes.IfNull.get;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_BARRAS;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_CODIGO;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_PESQUISA;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_PRODUTO;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_QUANTIDADE;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_UNIDADE;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_VALOR_TOTAL;
import static br.com.sysdesc.util.resources.Resources.TBLENTRADA_VALOR_UNITARIO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import br.com.sysdesc.repository.model.EntradaDetalhe;
import br.com.sysdesc.repository.model.Produto;
import br.com.sysdesc.repository.model.Unidade;
import br.com.sysdesc.util.classes.BigDecimalUtil;
import br.com.sysdesc.util.classes.ListUtil;

public class EntradaMercadoriasTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<String> configuracoesPesquisa = new ArrayList<>();
	private List<EntradaDetalhe> rows = ListUtil.toList(new EntradaDetalhe());

	public EntradaMercadoriasTableModel() {
		configuracoesPesquisa.add(translate(TBLENTRADA_PESQUISA));
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

		EntradaDetalhe entrada = rows.get(rowIndex);

		switch (columnIndex) {

		case 1:
			return getPropertie(getPropertie(entrada, EntradaDetalhe::getProduto), Produto::getIdProduto);
		case 2:
			return getPropertie(getPropertie(entrada, EntradaDetalhe::getProduto), Produto::getCodigoBarras);
		case 3:
			return getPropertie(getPropertie(entrada, EntradaDetalhe::getProduto), Produto::getDescricao);
		case 4:
			return getPropertie(getPropertie(getPropertie(entrada, EntradaDetalhe::getProduto), Produto::getUnidade),
					Unidade::getDescricaoReduzida);
		case 5:
			return entrada.getQuantidade();
		case 6:
			return entrada.getValorUnitario();
		case 7:
			return entrada.getValorTotal();
		}
		return "";
	}

	private <K, P> P getPropertie(K objeto, Function<K, P> function) {

		if (objeto == null) {

			return null;
		}

		return function.apply(objeto);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		if (columnIndex != 5 && columnIndex != 6) {
			return;
		}

		EntradaDetalhe entradaDetalhe = getRow(rowIndex);

		if (columnIndex == 5) {

			entradaDetalhe.setQuantidade((BigDecimal) aValue);

		} else if (columnIndex == 6) {

			entradaDetalhe.setValorUnitario((BigDecimal) aValue);
		}

		BigDecimal valorTotal = get(entradaDetalhe.getQuantidade(), BigDecimalUtil.ZERO)
				.multiply(get(entradaDetalhe.getValorUnitario(), BigDecimalUtil.ZERO));

		entradaDetalhe.setValorTotal(valorTotal.setScale(2, RoundingMode.HALF_EVEN));

		fireTableCellUpdated(rowIndex, 7);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		if (columnIndex == 0) {
			return JPanel.class;
		}

		if (columnIndex == 3 || columnIndex == 4) {
			return String.class;
		}

		if (columnIndex == 1 || columnIndex == 2) {
			return Long.class;
		}

		return BigDecimal.class;
	}

	@Override
	public boolean isCellEditable(int row, int column) {

		return (column == 0 || ((column == 5 || column == 6) && (this.rows.get(row).getProduto() != null)));
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

	public EntradaDetalhe getRow(int selectedRow) {
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

	public List<EntradaDetalhe> getRows() {
		return rows;
	}

	public void setRows(List<EntradaDetalhe> rows) {
		this.rows = rows;
		fireTableDataChanged();
	}

	public void addRow() {

		if (this.rows.stream().noneMatch(x -> x.getProduto() == null)) {

			this.rows.add(new EntradaDetalhe());

			fireTableDataChanged();
		}

	}
}

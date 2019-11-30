package br.com.sysdesc.service.kardex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.sysdesc.repository.dao.KardexDAO;
import br.com.sysdesc.repository.model.Kardex;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.BigDecimalUtil;
import br.com.sysdesc.util.classes.DateUtil;
import br.com.sysdesc.util.classes.SomaUtil;
import br.com.sysdesc.util.exception.SysDescException;
import br.com.sysdesc.util.vo.KardexVO;

public class KardexService extends AbstractGenericService<Kardex> {

	private KardexDAO kardexDAO;

	public KardexService() {
		this(new KardexDAO());
	}

	public KardexService(KardexDAO kardexDAO) {
		super(new KardexDAO(), Kardex::getIdKardex);
		this.kardexDAO = kardexDAO;
	}

	public List<KardexVO> buscarRegistroskardex(Long idEmpresa, Long idProduto, String operacao, Date dataInicial,
			Date datafinal) {

		List<KardexVO> kardexVOs = new ArrayList<>();

		Date dataEstoqueInicial = DateUtil.getInitialDate(dataInicial);

		SomaUtil somaValorTotal = new SomaUtil();
		SomaUtil somaQuantidadeTotal = new SomaUtil();

		KardexVO kardexVO = this.retornarEstoqueInicial(dataEstoqueInicial, somaQuantidadeTotal, somaValorTotal, "T",
				BigDecimal.ZERO, BigDecimal.ZERO);

		kardexVOs.add(kardexVO);

		List<Kardex> kardexes = kardexDAO.buscarRegistroskardex(idEmpresa, idProduto, operacao,
				DateUtil.getInitialDate(dataInicial), DateUtil.getFinalDate(datafinal));

		for (Kardex kardex : kardexes) {

			kardexVOs.add(retornarEstoqueInicial(kardex.getDataMovimento(), somaQuantidadeTotal, somaValorTotal,
					kardex.getFlagOperacao(), kardex.getQuantidade(), kardex.getValorTotal()));
		}

		return kardexVOs;
	}

	private KardexVO retornarEstoqueInicial(Date dataInicial, SomaUtil quantidadeTotal, SomaUtil valorTotal,
			String operacao, BigDecimal quantidade, BigDecimal valorOperacao) {

		KardexVO kardexVO = new KardexVO();
		kardexVO.setDataMovimento(dataInicial);
		kardexVO.setQuantidade(quantidade);
		kardexVO.setValorOperacao(valorOperacao);

		kardexVO.setTipoOperacao(getTipoOperacao(operacao));

		calcularSaldo(valorTotal, valorOperacao, operacao);
		calcularSaldo(quantidadeTotal, quantidade, operacao);

		BigDecimal valorUnitario = BigDecimal.ZERO;

		if (!BigDecimalUtil.isNullOrZero(valorTotal.getValue())) {

			valorUnitario = valorTotal.getValue().divide(quantidadeTotal.getValue(), 3, RoundingMode.HALF_EVEN);
		}

		kardexVO.setSaldoTotal(valorTotal.getValue());
		kardexVO.setQuantidadeTotal(quantidadeTotal.getValue());
		kardexVO.setCustoUnitario(valorUnitario);
		return kardexVO;
	}

	private BigDecimal calcularSaldo(SomaUtil somaUtil, BigDecimal valorOperacao, String operacao) {

		switch (operacao) {
		case "E":
		case "T":
			return somaUtil.somar(valorOperacao);
		case "S":
			return somaUtil.subtrair(valorOperacao);

		default:
			throw new SysDescException("Tipo de operação não suportado");
		}
	}

	private String getTipoOperacao(String operacao) {

		switch (operacao) {
		case "T":
			return "Estoque Inicial";
		case "E":
			return "Entrada";
		case "S":
			return "Saída";

		default:
			throw new SysDescException("Tipo de operação não suportado");
		}

	}

}

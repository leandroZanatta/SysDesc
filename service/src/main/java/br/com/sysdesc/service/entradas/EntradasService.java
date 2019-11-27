package br.com.sysdesc.service.entradas;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.sysdesc.repository.dao.EntradaCabecalhoDAO;
import br.com.sysdesc.repository.model.EntradaCabecalho;
import br.com.sysdesc.repository.model.Kardex;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.BigDecimalUtil;
import br.com.sysdesc.util.classes.ListUtil;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class EntradasService extends AbstractGenericService<EntradaCabecalho> {

	private final EntradaCabecalhoDAO entradaCabecalhoDAO;

	public EntradasService() {

		this(new EntradaCabecalhoDAO());
	}

	public EntradasService(EntradaCabecalhoDAO entradaCabecalhoDAO) {
		super(entradaCabecalhoDAO, EntradaCabecalho::getIdEntradaCabecalho);

		this.entradaCabecalhoDAO = entradaCabecalhoDAO;
	}

	@Override
	public void validar(EntradaCabecalho objetoPersistir) {
		if (objetoPersistir.getOperacaoEstoque() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_NATUREZA_OPERACAO);
		}
		if (LongUtil.isNullOrZero(objetoPersistir.getNumeroNota())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_NUNERO_NOTA);

		}

		if (objetoPersistir.getEmitente() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UM_EMITENTE);
		}
		if (objetoPersistir.getDataEmissao() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UMA_DATA_EMISSAO);
		}
		if (objetoPersistir.getDataSaida() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UMA_DATA_SAIDA);

		}
		if (BigDecimalUtil.isNullOrZero(objetoPersistir.getValorProdutos())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UMA_VALOR_PRODUTO);

		}
		if (BigDecimalUtil.isNullOrZero(objetoPersistir.getValorNota())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_UM_VALOR_NOTA);
		}

		if (ListUtil.isNullOrEmpty(objetoPersistir.getEntradaDetalhes())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_UM_ITEM_PARA_NOTA);
		}
		if (objetoPersistir.getEntradaDetalhes().stream().anyMatch(x -> x.getQuantidade() == null)) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_QUANTIDADE_PRODUTO_NOTA);
		}
		if (objetoPersistir.getEntradaDetalhes().stream().anyMatch(x -> x.getValorUnitario() == null)) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_VALOR_UNITARIO_PRODUTO);
		}
		Double somaItens = objetoPersistir.getEntradaDetalhes().stream()
				.mapToDouble(detalhe -> detalhe.getValorTotal().doubleValue()).sum();

		if (BigDecimalUtil.diferente(objetoPersistir.getValorNota(), BigDecimal.valueOf(somaItens))) {
			throw new SysDescException(MensagemConstants.MENSAGEM_VALOR_ITENS_DIFERENTE_DO_VALOR_DE_NOTAS);
		}
	}

	@Override
	public void salvar(EntradaCabecalho objetoPersistir) {

		EntityManager entityManager = entradaCabecalhoDAO.getEntityManager();

		entityManager.getTransaction().begin();

		entityManager.persist(objetoPersistir);

		List<Kardex> estoques = new ArrayList<>();

		objetoPersistir.getEntradaDetalhes().forEach(detalhe -> {

			if (detalhe.getProduto().getFlagMovimentaEstoque()
					&& detalhe.getEntradaCabecalho().getOperacaoEstoque().getAtualizacusto()) {

				Kardex kardex = new Kardex();
				kardex.setCodigoEmpresa(detalhe.getEntradaCabecalho().getCodigoEmpresa());
				kardex.setFlagOperacao(detalhe.getEntradaCabecalho().getOperacaoEstoque().getOperacao());
				kardex.setQuantidade(detalhe.getQuantidade());
				kardex.setDataMovimento(detalhe.getEntradaCabecalho().getDataEmissao());
				kardex.setCodigoProduto(detalhe.getProduto().getIdProduto());
				kardex.setQuantidadeDisponivel(detalhe.getQuantidade());
				kardex.setValorTotal(detalhe.getValorTotal());
				kardex.setValorUnitario(
						detalhe.getValorTotal().divide(detalhe.getQuantidade(), 8, RoundingMode.HALF_EVEN));

				estoques.add(kardex);
			}

		});

		if (!ListUtil.isNullOrEmpty(estoques)) {

			estoques.forEach(entityManager::persist);

		}

		entityManager.getTransaction().commit();

	}
}

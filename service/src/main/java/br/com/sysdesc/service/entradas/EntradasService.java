package br.com.sysdesc.service.entradas;

import br.com.sysdesc.repository.dao.EntradaCabecalhoDAO;
import br.com.sysdesc.repository.model.EntradaCabecalho;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class EntradasService extends AbstractGenericService<EntradaCabecalho> {

	public EntradasService() {

		super(new EntradaCabecalhoDAO(), EntradaCabecalho::getIdEntradaCabecalho);
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
if(objetoPersistir.getDataEmissao()==null) {
	throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UMA_DATA_EMISSAO);
	}
if(objetoPersistir.getDataSaida()==null) {
	throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UMA_DATA_SAIDA);
}
if(objetoPersistir.getValorFrete()==null) {
	throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UM_FRETE);
}
if(objetoPersistir.getValorProdutos()==null) {
	throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UMA_VALOR_PRODUTO);

	}
	}
}

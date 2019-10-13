package br.com.sysdesc.service.formaspagamento;

import br.com.sysdesc.repository.dao.FormasPagamentoDAO;
import br.com.sysdesc.repository.model.FormasPagamento;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.BigDecimalUtil;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class FormasPagamentoService extends AbstractGenericService<FormasPagamento> {

	public FormasPagamentoService() {
		super(new FormasPagamentoDAO(), FormasPagamento::getIdFormaPagamento);
	}

	@Override
	public void validar(FormasPagamento objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_DESCRICAO_VALIDA);
		}

		if (objetoPersistir.getFlagPermitePagamentoPrazo() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_FORMA_PAGAMAMENTO);
		}

		if (objetoPersistir.getFlagPermitePagamentoPrazo()) {

			if (LongUtil.isNullOrZero(objetoPersistir.getNumeroDiasPagamento())) {
				throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_NUMERO_DIAS);
			}

			if (BigDecimalUtil.isNullOrZero(objetoPersistir.getValorMinimoParcelas())) {
				throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_VALOR_PARCELAS);
			}

			if (LongUtil.isNullOrZero(objetoPersistir.getNumeroMaximoParcelas())) {
				throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_NUMERO_PARCELAS);
			}
		}
	}
}

package br.com.sysdesc.service.cidade;

import br.com.sysdesc.repository.dao.CidadeDAO;
import br.com.sysdesc.repository.model.Cidade;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class CidadeService extends AbstractGenericService<Cidade> {

	public CidadeService() {
		super(new CidadeDAO(), Cidade::getIdCidade);
	}

	@Override
	public void validar(Cidade objetoPersistir) {

		if (objetoPersistir.getEstado() == null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_ESTADO);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_DESCRICAO_VALIDA);
		}
	}
}

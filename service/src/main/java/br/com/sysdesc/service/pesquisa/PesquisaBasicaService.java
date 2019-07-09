package br.com.sysdesc.service.pesquisa;

import br.com.sysdesc.repository.dao.PesquisaNormalDAO;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class PesquisaBasicaService extends AbstractGenericService<Pesquisa> {

	public PesquisaBasicaService() {
		super(new PesquisaNormalDAO(), Pesquisa::getIdPesquisa);
	}

	@Override
	public void validar(Pesquisa objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}

		if (LongUtil.isNullOrZero(objetoPersistir.getPaginacao())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}

		if (LongUtil.isNullOrZero(objetoPersistir.getCodigoPesquisa())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}
	}

}

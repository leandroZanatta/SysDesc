package br.com.sysdesc.service.unidade;

import java.util.List;

import br.com.sysdesc.repository.dao.UnidadeDAO;
import br.com.sysdesc.repository.model.Unidade;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class UnidadeService extends AbstractGenericService<Unidade> {

	private final UnidadeDAO unidadeDAO;

	public UnidadeService() {
		this(new UnidadeDAO());
	}

	public UnidadeService(UnidadeDAO unidadeDAO) {
		super(unidadeDAO, Unidade::getIdUnidade);

		this.unidadeDAO = unidadeDAO;
	}

	@Override
	public void validar(Unidade objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricaoReduzida())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_REDUZIDA_VALIDA);
		}

		if (objetoPersistir.getDescricaoReduzida().length() > 3) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_REDUZIDA_MAXIMO_TRES);
		}

	}

	public List<Unidade> listarUnidades() {

		return unidadeDAO.listar();
	}
}

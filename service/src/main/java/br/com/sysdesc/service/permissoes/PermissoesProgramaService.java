package br.com.sysdesc.service.permissoes;

import java.util.List;

import br.com.sysdesc.repository.dao.PermissaoProgramaDAO;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class PermissoesProgramaService extends AbstractGenericService<PermissaoPrograma> {

	private PermissaoProgramaDAO permissaoProgramaDAO;

	public PermissoesProgramaService() {
		this(new PermissaoProgramaDAO());
	}

	public PermissoesProgramaService(PermissaoProgramaDAO permissaoProgramaDAO) {
		super(permissaoProgramaDAO, PermissaoPrograma::getIdPermissaoprograma);

		this.permissaoProgramaDAO = permissaoProgramaDAO;
	}

	@Override
	public void validar(PermissaoPrograma objetoPersistir) {

	}

	public void salvarTodos(List<PermissaoPrograma> permissoes) {

		permissaoProgramaDAO.salvar(permissoes);
	}

}

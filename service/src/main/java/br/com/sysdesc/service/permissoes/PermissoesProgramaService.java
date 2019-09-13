package br.com.sysdesc.service.permissoes;

import br.com.sysdesc.repository.dao.PermissaoProgramaDAO;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class PermissoesProgramaService extends AbstractGenericService<PermissaoPrograma> {

	public PermissoesProgramaService() {
		super(new PermissaoProgramaDAO(), PermissaoPrograma::getIdPermissaoprograma);
	}

	@Override
	public void validar(PermissaoPrograma objetoPersistir) {

	}
}

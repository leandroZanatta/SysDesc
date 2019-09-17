package br.com.sysdesc.service.planocontas;

import br.com.sysdesc.repository.dao.PlanoContasDAO;
import br.com.sysdesc.repository.model.PlanoContas;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class PlanoContasService extends AbstractGenericService<PlanoContas> {

	public PlanoContasService() {
		super(new PlanoContasDAO(), PlanoContas::getIdPlanoContas);

	}

	@Override
	public void validar(PlanoContas objetoPersistir) {

	}
}

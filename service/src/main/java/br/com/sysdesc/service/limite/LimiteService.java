package br.com.sysdesc.service.limite;

import br.com.sysdesc.repository.dao.LimiteDAO;
import br.com.sysdesc.repository.model.Limite;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class LimiteService extends AbstractGenericService<Limite> {

	public LimiteService() {
		super(new LimiteDAO(), Limite::getCodigoCliente);

	}

	@Override
	public void validar(Limite objetoPersistir) {

	}

}

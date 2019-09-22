package br.com.sysdesc.service.entradas;

import br.com.sysdesc.repository.dao.EntradaCabecalhoDAO;
import br.com.sysdesc.repository.model.EntradaCabecalho;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class EntradasService extends AbstractGenericService<EntradaCabecalho> {

	public EntradasService() {

		super(new EntradaCabecalhoDAO(), EntradaCabecalho::getIdEntradaCabecalho);
	}

	@Override
	public void validar(EntradaCabecalho objetoPersistir) {

	}

}

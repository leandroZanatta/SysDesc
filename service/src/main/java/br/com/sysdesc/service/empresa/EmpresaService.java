package br.com.sysdesc.service.empresa;

import br.com.sysdesc.repository.dao.EmpresaDAO;
import br.com.sysdesc.repository.model.Empresa;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class EmpresaService extends AbstractGenericService<Empresa> {

	private final EmpresaDAO empresaDAO;

	public EmpresaService() {
		this(new EmpresaDAO());
	}

	public EmpresaService(EmpresaDAO empresaDAO) {
		super(empresaDAO, Empresa::getIdEmpresa);

		this.empresaDAO = empresaDAO;
	}

	@Override
	public void validar(Empresa objetoPersistir) {

	}

}

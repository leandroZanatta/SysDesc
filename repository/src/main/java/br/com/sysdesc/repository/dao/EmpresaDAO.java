package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QEmpresa.empresa;

import br.com.sysdesc.repository.model.Empresa;

public class EmpresaDAO extends AbstractGenericDAO<Empresa> {

	public EmpresaDAO() {
		super(empresa, empresa.idEmpresa);
	}

}

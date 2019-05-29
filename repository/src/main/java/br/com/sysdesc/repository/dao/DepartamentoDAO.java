package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QDepartamento.departamento;

import br.com.sysdesc.repository.model.Departamento;

public class DepartamentoDAO extends AbstractGenericDAO<Departamento> {

	public DepartamentoDAO() {
		super(departamento, departamento.idDepartamento);
	}

}

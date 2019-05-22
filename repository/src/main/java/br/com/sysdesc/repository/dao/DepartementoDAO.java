package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QDepartamento.departamento;

import br.com.sysdesc.repository.model.Departamento;

public class DepartementoDAO extends AbstractGenericDAO<Departamento> {

	public DepartementoDAO() {
		super(departamento, departamento.idDepartamento);
	}

}

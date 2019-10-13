package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPrograma.programa1;

import java.util.List;

import br.com.sysdesc.repository.model.Programa;

public class ProgramaDAO extends AbstractGenericDAO<Programa> {

	public ProgramaDAO() {
		super(programa1, programa1.idPrograma);
	}

	public List<Programa> buscarRootProgramas() {
		return from().where(programa1.codigoModulo.isNull()).list(programa1);
	}
}

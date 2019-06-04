package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QEstado.estado;

import br.com.sysdesc.repository.model.Estado;

public class EstadoDAO extends AbstractGenericDAO<Estado> {

	public EstadoDAO() {
		super(estado, estado.idEstado);
	}

}

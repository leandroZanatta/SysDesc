package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QMarca.marca;

import br.com.sysdesc.repository.model.Marca;

public class MarcaDAO extends AbstractGenericDAO<Marca> {

	public MarcaDAO() {
		super(marca, marca.idMarca);
	}

}

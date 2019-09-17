package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPlanoContas.planoContas;

import br.com.sysdesc.repository.model.PlanoContas;

public class PlanoContasDAO extends AbstractGenericDAO<PlanoContas> {

	public PlanoContasDAO() {
		super(planoContas, planoContas.idPlanoContas);
	}

}

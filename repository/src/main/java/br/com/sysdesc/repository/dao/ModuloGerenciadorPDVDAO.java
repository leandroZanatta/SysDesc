package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QModuloGerenciadorPDV.moduloGerenciadorPDV;

import br.com.sysdesc.repository.model.ModuloGerenciadorPDV;

public class ModuloGerenciadorPDVDAO extends AbstractGenericDAO<ModuloGerenciadorPDV> {

	public ModuloGerenciadorPDVDAO() {
		super(moduloGerenciadorPDV, moduloGerenciadorPDV.idModuloGerenciadorPDV);
	}

}

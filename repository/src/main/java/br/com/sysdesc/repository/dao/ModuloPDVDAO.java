package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QModuloPDV.moduloPDV;

import br.com.sysdesc.repository.model.ModuloPDV;

public class ModuloPDVDAO extends AbstractGenericDAO<ModuloPDV> {

	public ModuloPDVDAO() {
		super(moduloPDV, moduloPDV.idModuloPDV);
	}

}

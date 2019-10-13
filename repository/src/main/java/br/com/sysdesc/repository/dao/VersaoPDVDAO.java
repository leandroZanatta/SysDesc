package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QVersaoPDV.versaoPDV1;

import br.com.sysdesc.repository.model.VersaoPDV;

public class VersaoPDVDAO extends AbstractGenericDAO<VersaoPDV> {

	public VersaoPDVDAO() {
		super(versaoPDV1, versaoPDV1.idVersaoPDV);
	}

}

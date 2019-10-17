package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPDVModuloGerenciadorPDV.pDVModuloGerenciadorPDV;

import java.util.List;

import br.com.sysdesc.repository.model.PDVModuloGerenciadorPDV;

public class PDVModuloGerenciadorPDVDAO extends AbstractGenericDAO<PDVModuloGerenciadorPDV> {

	public PDVModuloGerenciadorPDVDAO() {
		super(pDVModuloGerenciadorPDV, pDVModuloGerenciadorPDV.idPDVModuloGerenciadorPDV);
	}

	public List<PDVModuloGerenciadorPDV> buscarPorCodigoPDV(Long idPdv) {

		return from().where(pDVModuloGerenciadorPDV.codigoPDV.eq(idPdv)).list(pDVModuloGerenciadorPDV);
	}

}

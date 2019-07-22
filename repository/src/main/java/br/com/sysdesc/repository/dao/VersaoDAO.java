package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QVersao.versao1;

import br.com.sysdesc.repository.model.Versao;

public class VersaoDAO extends AbstractGenericDAO<Versao> {

    public VersaoDAO() {
        super(versao1, versao1.versao);
    }

}

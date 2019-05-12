package br.com.sysdesc.repository.enumeradores;

import br.com.sysdesc.repository.model.Usuario;

public enum TipoPesquisaEnum {

	USUARIO(1L, Usuario.class);

	private Long codigoTabela;
	private Class<?> classePesquisa;

	private TipoPesquisaEnum(Long codigoTabela, Class<?> classePesquisa) {
		this.codigoTabela = codigoTabela;
		this.classePesquisa = classePesquisa;
	}

	public Long getCodigoTabela() {
		return codigoTabela;
	}

	public void setCodigoTabela(Long codigoTabela) {
		this.codigoTabela = codigoTabela;
	}

	public Class<?> getClassePesquisa() {
		return classePesquisa;
	}

	public void setClassePesquisa(Class<?> classePesquisa) {
		this.classePesquisa = classePesquisa;
	}

}

package br.com.sysdesc.util.vo;

import java.util.List;

import br.com.sysdesc.util.enumeradores.ModificadoresEnum;

public class CriacaoClasseVO {

	private String nomeMetodo;

	private Class<?> tipoMetodo;

	private List<ModificadoresEnum> modificadores;

	private Boolean gerarGetterSetter;

	public CriacaoClasseVO(List<ModificadoresEnum> modificadores, String nomeMetodo, Class<?> tipoMetodo,
			Boolean gerarGetterSetter) {
		this.modificadores = modificadores;
		this.nomeMetodo = nomeMetodo;
		this.tipoMetodo = tipoMetodo;
		this.gerarGetterSetter = gerarGetterSetter;
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}

	public Class<?> getTipoMetodo() {
		return tipoMetodo;
	}

	public void setTipoMetodo(Class<?> tipoMetodo) {
		this.tipoMetodo = tipoMetodo;
	}

	public List<ModificadoresEnum> getModificadores() {
		return modificadores;
	}

	public void setModificadores(List<ModificadoresEnum> modificadores) {
		this.modificadores = modificadores;
	}

	public Boolean getGerarGetterSetter() {
		return gerarGetterSetter;
	}

	public void setGerarGetterSetter(Boolean gerarGetterSetter) {
		this.gerarGetterSetter = gerarGetterSetter;
	}

}

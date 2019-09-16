package br.com.sysdesc.util.vo;

import java.util.ArrayList;
import java.util.List;

public class PermissaoProgramaVO {

	private String descricao;

	private Long idPrograma;

	private Boolean flagLeitura = Boolean.FALSE;

	private Boolean flagCadastro = Boolean.FALSE;

	private Boolean flagExclusao = Boolean.FALSE;

	private List<PermissaoProgramaVO> childs = new ArrayList<>();

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getFlagLeitura() {
		return flagLeitura;
	}

	public void setFlagLeitura(Boolean flagLeitura) {
		this.flagLeitura = flagLeitura;
	}

	public Boolean getFlagCadastro() {
		return flagCadastro;
	}

	public void setFlagCadastro(Boolean flagCadastro) {
		this.flagCadastro = flagCadastro;
	}

	public Boolean getFlagExclusao() {
		return flagExclusao;
	}

	public void setFlagExclusao(Boolean flagExclusao) {
		this.flagExclusao = flagExclusao;
	}

	public List<PermissaoProgramaVO> getChilds() {
		return childs;
	}

	public void setChilds(List<PermissaoProgramaVO> childs) {
		this.childs = childs;
	}

	public void addChild(PermissaoProgramaVO child) {

		this.childs.add(child);
	}

}

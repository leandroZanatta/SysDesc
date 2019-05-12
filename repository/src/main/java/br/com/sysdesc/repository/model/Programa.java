package br.com.sysdesc.repository.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tb_programa database table.
 * 
 */
@Entity
@Table(name="tb_programa")
@NamedQuery(name="Programa.findAll", query="SELECT p FROM Programa p")
public class Programa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_programa")
	private long idPrograma;

	@Column(name="tx_descricao")
	private String txDescricao;

	//bi-directional many-to-one association to PermissaoPrograma
	@OneToMany(mappedBy="tbPrograma")
	private List<PermissaoPrograma> tbPermissaoprogramas;

	public Programa() {
	}

	public long getIdPrograma() {
		return this.idPrograma;
	}

	public void setIdPrograma(long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getTxDescricao() {
		return this.txDescricao;
	}

	public void setTxDescricao(String txDescricao) {
		this.txDescricao = txDescricao;
	}

	public List<PermissaoPrograma> getTbPermissaoprogramas() {
		return this.tbPermissaoprogramas;
	}

	public void setTbPermissaoprogramas(List<PermissaoPrograma> tbPermissaoprogramas) {
		this.tbPermissaoprogramas = tbPermissaoprogramas;
	}

	public PermissaoPrograma addTbPermissaoprograma(PermissaoPrograma tbPermissaoprograma) {
		getTbPermissaoprogramas().add(tbPermissaoprograma);
		tbPermissaoprograma.setTbPrograma(this);

		return tbPermissaoprograma;
	}

	public PermissaoPrograma removeTbPermissaoprograma(PermissaoPrograma tbPermissaoprograma) {
		getTbPermissaoprogramas().remove(tbPermissaoprograma);
		tbPermissaoprograma.setTbPrograma(null);

		return tbPermissaoprograma;
	}

}
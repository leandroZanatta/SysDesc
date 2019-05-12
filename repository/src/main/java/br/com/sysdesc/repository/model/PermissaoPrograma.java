package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the tb_permissaoprograma database table.
 * 
 */
@Entity
@Table(name = "tb_permissaoprograma")
@NamedQuery(name = "PermissaoPrograma.findAll", query = "SELECT p FROM PermissaoPrograma p")
public class PermissaoPrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TB_PERMISSAOPROGRAMA_IDPERMISSAOPROGRAMA_GENERATOR", sequenceName = "GEN_PERMISSAOPROGRAMA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_PERMISSAOPROGRAMA_IDPERMISSAOPROGRAMA_GENERATOR")
	@Column(name = "id_permissaoprograma")
	private long idPermissaoprograma;

	@Column(name = "fl_cadastro")
	private Boolean flCadastro;

	@Column(name = "fl_exclusao")
	private Boolean flExclusao;

	@Column(name = "fl_leitura")
	private Boolean flLeitura;

	@ManyToOne
	@JoinColumn(name = "cd_programa")
	private Programa programa;

	@ManyToOne
	@JoinColumn(name = "cd_usuario")
	private Usuario usuario;

}
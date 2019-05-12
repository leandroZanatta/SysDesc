package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_permissaoprograma")
@SequenceGenerator(name = "GEN_PERMISSAOPROGRAMA", sequenceName = "GEN_PERMISSAOPROGRAMA")
public class PermissaoPrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PERMISSAOPROGRAMA")
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

	@ManyToOne
	@JoinColumn(name = "cd_perfil")
	private Perfil perfil;

}
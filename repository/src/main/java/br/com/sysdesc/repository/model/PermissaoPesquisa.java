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
@Table(name = "tb_permissaopesquisa")
@SequenceGenerator(name = "GEN_PERMISSAOPESQUSA", sequenceName = "GEN_PERMISSAOPESQUSA")
public class PermissaoPesquisa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PERMISSAOPESQUSA")
	@Column(name = "id_permissaopesquisa")
	private long idPermissaopesquisa;

	@Column(name = "cd_usuario", insertable = false, updatable = false)
	private Long codigoUsuario;

	@Column(name = "cd_perfil", insertable = false, updatable = false)
	private Long codigoPerfil;

	@Column(name = "cd_pesquisa", insertable = false, updatable = false)
	private Long codigoPesquisa;

	@ManyToOne
	@JoinColumn(name = "cd_perfil")
	private Perfil perfil;

	@ManyToOne
	@JoinColumn(name = "cd_pesquisa")
	private Pesquisa pesquisa;

	@ManyToOne
	@JoinColumn(name = "cd_usuario")
	private Usuario usuario;
}
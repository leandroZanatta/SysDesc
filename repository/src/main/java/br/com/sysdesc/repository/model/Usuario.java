package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuario")
@SequenceGenerator(name = "GEN_USUARIO", sequenceName = "GEN_USUARIO")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_USUARIO")
	@Column(name = "id_usuario")
	private Long idUsuario;

	@Column(name = "tx_senha")
	private String senha;

	@Column(name = "tx_usuario")
	private String usuario;

	@ManyToOne
	@JoinColumn(name = "cd_cliente")
	private Cliente cliente;

	@OneToMany(mappedBy = "usuario")
	private List<PermissaoPrograma> permissaoProgramas;

	@OneToMany(mappedBy = "usuario")
	private List<PerfilUsuario> perfilUsuarios;

	@OneToMany(mappedBy = "usuario")
	private List<PermissaoPesquisa> permissaoPesquisas;

}
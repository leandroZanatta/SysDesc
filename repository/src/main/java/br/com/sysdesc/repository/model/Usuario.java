package br.com.sysdesc.repository.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tb_usuario database table.
 * 
 */
@Entity
@Table(name = "tb_usuario")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TB_USUARIO_IDUSUARIO_GENERATOR", sequenceName = "GEN_USUARIO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_USUARIO_IDUSUARIO_GENERATOR")
	@Column(name = "id_usuario")
	private long idUsuario;

	@Column(name = "cd_cliente")
	private java.math.BigDecimal cdCliente;

	@Column(name = "tx_senha")
	private String txSenha;

	@Column(name = "tx_usuario")
	private String txUsuario;

	// bi-directional many-to-one association to PermissaoPrograma
	@OneToMany(mappedBy = "tbUsuario")
	private List<PermissaoPrograma> tbPermissaoprogramas;

}
package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_USUARIO")
	@Column(name = "id_usuario")
	private long idUsuario;

	@Column(name = "tx_senha")
	private String senha;

	@Column(name = "tx_usuario")
	private String usuario;

	@OneToMany(mappedBy = "usuario")
	private List<PermissaoPrograma> permissaoProgramas;

	@ManyToMany
	@JoinTable(name = "tb_perfilusuario", joinColumns = { @JoinColumn(name = "cd_usuario") }, inverseJoinColumns = {
			@JoinColumn(name = "cd_perfil") })
	private List<Perfil> perfils;

	@ManyToOne
	@JoinColumn(name = "cd_cliente")
	private Cliente cliente;

}
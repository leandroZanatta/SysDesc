package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_perfil")
@SequenceGenerator(name = "GEN_PERFIL", allocationSize = 1, sequenceName = "GEN_PERFIL")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PERFIL")
	@Column(name = "id_perfil")
	private Long idPerfil;

	@Column(name = "tx_descricao")
	private String descricao;

	@ManyToMany(mappedBy = "perfils")
	private List<Pesquisa> pesquisas;

	@OneToMany(mappedBy = "perfil")
	private List<PerfilUsuario> perfilUsuarios;

	@OneToMany(mappedBy = "perfil")
	private List<PermissaoPrograma> permissaoProgramas;

}
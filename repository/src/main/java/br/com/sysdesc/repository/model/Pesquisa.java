package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_pesquisa")
@SequenceGenerator(name = "GEN_PESQUISA", allocationSize = 1, sequenceName = "GEN_PESQUISA")
public class Pesquisa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_PESQUISA")
	@Column(name = "id_pesquisa")
	private Long idPesquisa;

	@Column(name = "cd_pesquisa")
	private Long codigoPesquisa;

	@Column(name = "tx_descricao")
	private String descricao;

	@Column(name = "nr_paginacao")
	private Long paginacao;

	@Column(name = "cd_tipo")
	private Long tipo;

	@OneToMany(mappedBy = "pesquisa", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PermissaoPesquisa> permissaoPesquisas = new ArrayList<>();

	@OneToMany(mappedBy = "pesquisa", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PesquisaCampo> pesquisaCampos = new ArrayList<>();

}
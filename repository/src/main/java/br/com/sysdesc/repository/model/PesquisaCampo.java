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
@Table(name = "tb_pesquisacampo")
@SequenceGenerator(name = "GEN_PESQUISACAMPO", allocationSize = 1, sequenceName = "GEN_PESQUISACAMPO")
public class PesquisaCampo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_PESQUISACAMPO")
	@Column(name = "id_pesquisacampo")
	private Long idPesquisacampo;

	@Column(name = "cd_pesquisa", insertable = false, updatable = false)
	private Long codigoPesquisa;

	@Column(name = "fl_formatacao")
	private Long flagFormatacao;

	@Column(name = "fl_ordem")
	private Long flagOrdem;

	@Column(name = "fl_tipodado")
	private Long flagTipoDado;

	@Column(name = "fl_tipotamanho")
	private Long flagTipoTamanho;

	@Column(name = "nr_tamanho")
	private Long numeroTamanho;

	@Column(name = "tx_alias")
	private String alias;

	@Column(name = "tx_campo")
	private String campo;

	@Column(name = "tx_descricao")
	private String descricao;

	@Column(name = "tx_formato")
	private String formato;

	@ManyToOne
	@JoinColumn(name = "cd_pesquisa")
	private Pesquisa pesquisa;

}
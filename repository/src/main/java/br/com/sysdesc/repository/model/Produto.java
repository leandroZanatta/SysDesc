package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "tb_produto")
@SequenceGenerator(name = "GEN_PRODUTO", allocationSize = 1, sequenceName = "GEN_PRODUTO")
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_PRODUTO")
	@Column(name = "id_produto")
	private Long idProduto;

	@Column(name = "tx_descricao")
	private String descricao;

	@Column(name = "nr_codigobarras")
	private Long codigoBarras;

	@Column(name = "vl_estoqueminimo")
	private BigDecimal valorEstoqueMinimo;

	@Column(name = "vl_estoquemaximo")
	private BigDecimal valorEstoqueMaximo;

	@Column(name = "fl_quantidadefracionada")
	private Boolean flagQuantidadeFracionada;

	@Column(name = "fl_movimentaestoque")
	private Boolean flagMovimentaEstoque;

	@Column(name = "cd_tipo")
	private Long codigoTipo;

	@Column(name = "cd_status")
	private Long codigoStatus;

	@ManyToOne
	@JoinColumn(name = "cd_subcategoria")
	private Subcategoria subcategoria;

	@ManyToOne
	@JoinColumn(name = "cd_fornecedor")
	private Cliente fornecedor;

	@ManyToOne
	@JoinColumn(name = "cd_unidade")
	private Unidade unidade;

	@ManyToOne
	@JoinColumn(name = "cd_marca")
	private Marca marca;

}
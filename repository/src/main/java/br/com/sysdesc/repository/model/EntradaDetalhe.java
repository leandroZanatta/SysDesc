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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_entradadetalhe")
@SequenceGenerator(name = "GEN_ENTRADADETALHE", allocationSize = 1, sequenceName = "GEN_ENTRADADETALHE")
public class EntradaDetalhe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_ENTRADADETALHE")
	@Column(name = "id_entradadetalhe")
	private Long idEntradaDetalhe;

	@ManyToOne
	@JoinColumn(name = "cd_entradacabecalho")
	private EntradaCabecalho entradaCabecalho;

	@Column(name = "cd_entradacabecalho", insertable = false, updatable = false)
	private Long codigoEntradaCabecalho;

	@Column(name = "cd_produto", insertable = false, updatable = false)
	private Long codigoProduto;

	@Column(name = "nr_quantidade")
	private BigDecimal quantidade;

	@Column(name = "vl_unitario")
	private BigDecimal valorUnitario;

	@Column(name = "vl_total")
	private BigDecimal valorTotal;

	@ManyToOne
	@JoinColumn(name = "cd_produto")
	private Produto produto;

}
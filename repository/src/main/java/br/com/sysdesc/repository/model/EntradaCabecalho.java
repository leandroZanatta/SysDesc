package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_entradacabecalho")
@SequenceGenerator(name = "GEN_ENTRADACABECALHO", allocationSize = 1, sequenceName = "GEN_ENTRADACABECALHO")
public class EntradaCabecalho implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_ENTRADACABECALHO")
	@Column(name = "id_entradacabecalho")
	private Long idEntradaCabecalho;

	@Column(name = "cd_operacaoestoque", insertable = false, updatable = false)
	private Long codigoOperacaoEstoque;

	@Column(name = "cd_emitente", insertable = false, updatable = false)
	private Long codigoEmitente;

	@Column(name = "cd_empresa")
	private Long codigoEmpresa;

	@ManyToOne
	@JoinColumn(name = "cd_operacaoestoque")
	private OperacaoEstoque operacaoEstoque;

	@ManyToOne
	@JoinColumn(name = "cd_emitente")
	private Fornecedor emitente;

	@Column(name = "nr_nota")
	private Long numeroNota;

	@Column(name = "dt_emissao")
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;

	@Column(name = "dt_saida")
	@Temporal(TemporalType.DATE)
	private Date dataSaida;

	@Column(name = "vl_frete")
	private BigDecimal valorFrete;

	@Column(name = "vl_produtos")
	private BigDecimal valorProdutos;

	@Column(name = "vl_nota")
	private BigDecimal valorNota;

	@OneToMany(mappedBy = "entradaCabecalho", cascade = CascadeType.ALL)
	private List<EntradaDetalhe> entradaDetalhes = new ArrayList<>();

}
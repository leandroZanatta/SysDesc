package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_kardex")
@SequenceGenerator(name = "GEN_KARDEX", sequenceName = "GEN_KARDEX", allocationSize = 1)
public class Kardex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_kardex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_KARDEX")
	private Long idKardex;

	@Column(name = "cd_produto")
	private Long codigoProduto;
	
	@Column(name = "cd_empresa")
	private Long codigoEmpresa;

	@Column(name = "dt_movimento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataMovimento;
	
	@Column(name = "nr_quantidade")
	private BigDecimal quantidade;
	
	@Column(name = "nr_disponivel")
	private BigDecimal quantidadeDisponivel;
	
	@Column(name = "fl_operacao")
	private String flagOperacao;
	
	@Column(name = "vl_unitario")
	private BigDecimal valorUnitario;
	
	@Column(name = "vl_total")
	private BigDecimal valorTotal;
}
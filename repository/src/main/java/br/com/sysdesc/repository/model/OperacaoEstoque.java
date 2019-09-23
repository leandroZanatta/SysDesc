package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_operacoesestoque")
@SequenceGenerator(name = "GEN_OPERACOESESTOQUE", sequenceName = "GEN_OPERACOESESTOQUE", allocationSize = 1)
public class OperacaoEstoque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_operacoesestoque")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_OPERACOESESTOQUE")
	private Long idOperacoesEstoque;

	@Column(name = "tx_descricao")
	private String descricao;

	@Column(name = "cd_planoconta", insertable = false, updatable = false)
	private Long codigoConta;

	@ManyToOne
	@JoinColumn(name = "cd_planoconta")
	private PlanoContas planoContas;

	@Column(name = "fl_operacao")
	private String operacao;

	@Column(name = "fl_atualizacusto")
	private Boolean atualizacusta;

	@Column(name = "dt_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cadastro;

	@Column(name = "dt_manutencao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date manutencao;

}
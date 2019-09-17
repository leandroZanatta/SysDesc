package br.com.sysdesc.repository.model;

import java.io.Serializable;
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
@Table(name = "tb_planocontas")
@SequenceGenerator(name = "GEN_PLANOCONTAS", sequenceName = "GEN_PLANOCONTAS", allocationSize = 1)
public class PlanoContas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_planocontas")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_PLANOCONTAS")
	private Long idPlanoContas;

	@Column(name = "cd_contaprincipal")
	private String contaPrincipal;

	@Column(name = "tx_identificador")
	private String identificador;
	
	@Column(name = "tx_descricao")
	private String descricao;
	
	@Column(name = "fl_contaanalitica")
	private Boolean contaAnalitica;
	
	@Column(name = "fl_saldo")
	private Boolean saldo;
	
	@Column(name = "dt_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cadastro;
	
	@Column(name = "dt_manutencao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date manutencao;
	
	@Column(name = "nr_situacao")
	private Long situacao;
}
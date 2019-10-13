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
@Table(name = "tb_versaopdv")
@SequenceGenerator(name = "GEN_VERSAOPDV", sequenceName = "GEN_VERSAOPDV", allocationSize = 1)
public class VersaoPDV implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_versaopdv")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_VERSAOPDV")
	private Long idVersaoPDV;

	@Column(name = "nr_versaopdv")
	private String versaoPDV;

	@Column(name = "nr_versaogerenciador")
	private String versaoGerenciador;

	@Column(name = "dt_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
}
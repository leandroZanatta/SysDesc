package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_sincronizacaotabelas")
public class SincronizacaoTabelas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cd_tabela")
	private Long codigotabela;

	@Column(name = "dt_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;

	@Column(name = "nr_sincronizacaoversao")
	private Long sincronizacaoVersao;

}
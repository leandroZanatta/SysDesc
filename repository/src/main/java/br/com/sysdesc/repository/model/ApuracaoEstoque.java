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
@Table(name = "tb_apuracaoestoque")
@SequenceGenerator(name = "GEN_APURACAOESTOQUE", sequenceName = "GEN_APURACAOESTOUE", allocationSize = 1)
public class ApuracaoEstoque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_apuracaoestoque")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_APURACAOESTOQUE")
	private Long idApuracaoEstoque;
	
	@Column(name = "cd_empresa")
	private Long codigoEmpresa;

	@Column(name = "cd_apuracaoestoque")
	private String codigoApuracaoEstoque;
	
	@Column(name = "dt_inicio")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column(name = "dt_fim")
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
}
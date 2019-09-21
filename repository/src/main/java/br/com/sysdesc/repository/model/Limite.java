package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_limite")

public class Limite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cd_cliente")
	private Long codigoCliente;
	
	@Column(name = "vl_limitecheque")
	private BigDecimal valorLimiteCheque;
	
	@Column(name = "vl_limiteconvenio")
	private BigDecimal valorLimiteConvenio;
	
	@Column(name = "vl_limitecrediario")
	private BigDecimal valorLimiteCrediario;

	
}
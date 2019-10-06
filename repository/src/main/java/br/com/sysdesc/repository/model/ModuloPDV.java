package br.com.sysdesc.repository.model;

import java.io.Serializable;

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
@Table(name = "tb_modulopdv")
@SequenceGenerator(name = "GEN_MODULOPDV", allocationSize = 1, sequenceName = "GEN_MODULOPDV")
public class ModuloPDV implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_MODULOPDV")
	@Column(name = "id_modulopdv")
	private Long idModuloPDV;

	@Column(name = "cd_pdv", insertable = false, updatable = false)
	private Long codigoPDV;

	@Column(name = "cd_modulo", insertable = false, updatable = false)
	private Long codigoModulo;

	@ManyToOne
	@JoinColumn(name = "cd_pdv")
	private Pdv pdv;

	@ManyToOne
	@JoinColumn(name = "cd_modulo")
	private Modulo modulo;

	@Column(name = "tx_ip")
	private String ipModulo;
}
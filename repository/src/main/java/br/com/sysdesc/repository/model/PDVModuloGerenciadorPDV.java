package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name = "tb_pdvmodulogerenciadorpdv")
@SequenceGenerator(name = "GEN_PDVMODULOGERENCIADORPDV", allocationSize = 1, sequenceName = "GEN_PDVMODULOGERENCIADORPDV")
public class PDVModuloGerenciadorPDV implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_PDVMODULOGERENCIADORPDV")
	@Column(name = "id_pdvmodulogerenciadorpdv")
	private Long idPDVModuloGerenciadorPDV;

	@Column(name = "cd_pdv", insertable = false, updatable = false)
	private Long codigoPDV;

	@Column(name = "cd_modulogerenciadorpdv", insertable = false, updatable = false)
	private Long codigoModuloGerenciadorPDV;

	@ManyToOne
	@JoinColumn(name = "cd_pdv")
	private Pdv pdv;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cd_modulogerenciadorpdv")
	private ModuloGerenciadorPDV moduloGerenciadorPDV;

}
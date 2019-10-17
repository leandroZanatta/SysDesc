package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.List;

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

import lombok.Data;

@Data
@Entity
@Table(name = "tb_modulogerenciadorpdv")
@SequenceGenerator(name = "GEN_MODULOGERENCIADORPDV", allocationSize = 1, sequenceName = "GEN_MODULOGERENCIADORPDV")
public class ModuloGerenciadorPDV implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_MODULOGERENCIADORPDV")
	@Column(name = "id_modulogerenciadorpdv")
	private Long idModuloGerenciadorPDV;

	@Column(name = "cd_gerenciadorpdv")
	private Long codigoGerenciadorPDV;

	@Column(name = "cd_modulopdv")
	private Long codigoModuloPDV;

	@ManyToOne
	@JoinColumn(name = "cd_gerenciadorpdv", insertable = false, updatable = false)
	private GerenciadorPDV gerenciadorPDV;

	@ManyToOne()
	@JoinColumn(name = "cd_modulopdv", insertable = false, updatable = false)
	private ModuloPDV moduloPDV;

	@Column(name = "nr_porta")
	private Long codigoPorta;

	@OneToMany(mappedBy = "moduloGerenciadorPDV")
	private List<PDVModuloGerenciadorPDV> moduloGerenciadorPDVs;
}
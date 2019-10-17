package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_gerenciadorpdv")
@SequenceGenerator(name = "GEN_GERENCIADORPDV", allocationSize = 1, sequenceName = "GEN_GERENCIADORPDV")
public class GerenciadorPDV implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_GERENCIADORPDV")
	@Column(name = "id_gerenciadorpdv")
	private Long idGerenciadorPDV;

	@Column(name = "tx_ip")
	private String ipGerenciador;

	@Column(name = "nr_porta")
	private Long codigoPorta;

	@OneToMany(mappedBy = "gerenciadorPDV")
	private List<ModuloGerenciadorPDV> moduloGerenciadorPDVs;

}
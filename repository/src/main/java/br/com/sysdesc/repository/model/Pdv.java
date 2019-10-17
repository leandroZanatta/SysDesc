package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "tb_pdv")
@SequenceGenerator(name = "GEN_PDV", allocationSize = 1, sequenceName = "GEN_PDV")
public class Pdv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_PDV")
	@Column(name = "id_pdv")
	private Long idPdv;

	@Column(name = "nr_pdv")
	private Long numeroPDV;

	@Column(name = "tx_ip")
	private String ipPDV;

	@Column(name = "nr_situacao")
	private Long situacao;

	@OneToMany(mappedBy = "pdv", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PDVModuloGerenciadorPDV> moduloGerenciadorPDVs = new ArrayList<>();

}
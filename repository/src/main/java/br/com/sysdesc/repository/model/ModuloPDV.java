package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_modulopdv")
public class ModuloPDV implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_modulopdv")
	private Long idModuloPDV;

	@Column(name = "tx_descricao")
	private String descricao;

	@Column(name = "fl_desacoplar")
	private String desacoplado;

	@Column(name = "fl_possuiporta")
	private String possuiPorta;

	@Column(name = "nr_portapadrao")
	private Long portaPadrao;

	@OneToMany(mappedBy = "moduloPDV")
	private List<ModuloGerenciadorPDV> moduloGerenciadorPDVs;

}
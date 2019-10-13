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
@Table(name = "tb_modulo")
public class Modulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_modulo")
	private Long idModulo;

	@Column(name = "tx_descricao")
	private String descricao;

	@Column(name = "fl_desacoplar")
	private String desacoplado;

	@OneToMany(mappedBy = "modulo")
	private List<ModuloPDV> moduloPDVs;

}
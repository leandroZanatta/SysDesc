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
@Table(name = "tb_programa")
public class Programa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_programa")
	private long idPrograma;

	@Column(name = "tx_descricao")
	private String descricao;

	@OneToMany(mappedBy = "programa")
	private List<PermissaoPrograma> permissaoProgramas;

}
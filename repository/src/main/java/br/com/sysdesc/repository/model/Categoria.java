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
@Table(name = "tb_categoria")
@SequenceGenerator(name = "GEN_CATEGORIA", allocationSize = 1, sequenceName = "GEN_CATEGORIA")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CATEGORIA")
	@Column(name = "id_categoria")
	private Long idCategoria;

	@Column(name = "tx_descricao")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "cd_departamento")
	private Departamento departamento;

}
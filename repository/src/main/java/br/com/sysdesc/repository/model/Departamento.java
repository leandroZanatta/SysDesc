package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_deapratmento")
@SequenceGenerator(name = "GEN_DEPARTAMENTO", allocationSize = 1, sequenceName = "GEN_DEPARTAMENTO")
public class Departamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_DEPARTAMENTO")
	@Column(name = "id_departamento")
	private Long idDepartamento;

	@Column(name = "tx_descricao")
	private String descricao;

}
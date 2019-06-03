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
@Table(name = "tb_marca")
@SequenceGenerator(name = "GEN_MARCA", allocationSize = 1, sequenceName = "GEN_MARCA")
public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_MARCA")
	@Column(name = "id_marca")
	private Long idMarca;

	@Column(name = "tx_descricao")
	private String descricao;

}
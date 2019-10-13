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
@Table(name = "tb_subcategoria")
@SequenceGenerator(name = "GEN_SUBCATEGORIA", allocationSize = 1, sequenceName = "GEN_SUBCATEGORIA")
public class Subcategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_SUBCATEGORIA")
	@Column(name = "id_subcategoria")
	private Long idSubcategoria;

	@Column(name = "cd_categoria", insertable = false, updatable = false)
	private Long codigoCategoria;

	@Column(name = "tx_descricao")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "cd_categoria")
	private Categoria categoria;

	@OneToMany(mappedBy = "subcategoria")
	private List<Produto> produtos;

}
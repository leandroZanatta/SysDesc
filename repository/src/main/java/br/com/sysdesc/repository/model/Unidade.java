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
@Table(name = "tb_unidade")
@SequenceGenerator(name = "GEN_UNIDADE", allocationSize = 1, sequenceName = "GEN_UNIDADE")
public class Unidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_UNIDADE")
	@Column(name = "id_unidade")
	private Long idUnidade;

	@Column(name = "tx_descricao")
	private String descricao;

	@Column(name = "tx_descricaoreduzida")
	private String descricaoReduzida;

	@OneToMany(mappedBy = "unidade")
	private List<Produto> produtos;

	@Override
	public String toString() {
		return descricao;
	}
}
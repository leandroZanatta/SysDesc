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
@Table(name = "tb_cliente")
@SequenceGenerator(name = "GEN_CLIENTE", allocationSize = 1, sequenceName = "GEN_CLIENTE")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CLIENTE")
	@Column(name = "id_cliente")
	private Long idCliente;

	@Column(name = "tx_nome")
	private String nome;

	@ManyToOne
	@JoinColumn(name = "cd_cidade")
	private Cidade cidade;

	@OneToMany(mappedBy = "cliente")
	private List<Usuario> usuarios;

	@OneToMany(mappedBy = "fornecedor")
	private List<Produto> produtos;

}
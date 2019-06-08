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
@Table(name = "tb_pesquisa")
@SequenceGenerator(name = "GEN_PESQUISA", allocationSize = 1, sequenceName = "GEN_PESQUISA")
public class Pesquisa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PESQUISA")
	@Column(name = "id_pesquisa")
	private Long idPesquisa;

	@Column(name = "cd_pesquisa")
	private Long codigoPesquisa;

	@OneToMany(mappedBy = "pesquisa")
	private List<PermissaoPesquisa> permissaoPesquisas;

}
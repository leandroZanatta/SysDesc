package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.sysdesc.repository.model.pk.PesquisaPadraoPk;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_pesquisapadrao")
public class PesquisaPadrao implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PesquisaPadraoPk id;

	@Column(name = "cd_pesquisa")
	private Long codigoPesquisa;

	@Column(name = "cd_usuario")
	private Long codigoUsuario;

	@OneToOne()
	@JoinColumn(name = "cd_pesquisa", referencedColumnName = "id_pesquisa", insertable = false, updatable = false)
	private Pesquisa pesquisa;
}
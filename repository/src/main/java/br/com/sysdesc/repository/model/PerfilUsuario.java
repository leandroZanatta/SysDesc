package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.sysdesc.repository.model.pk.PerfilUsuarioPk;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_perfilusuario")
public class PerfilUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PerfilUsuarioPk id;

	@Column(name = "cd_usuario", insertable = false, updatable = false)
	private Long codigoUsuario;

	@Column(name = "cd_perfil", insertable = false, updatable = false)
	private Long codigoPerfil;

	@ManyToOne
	@JoinColumn(name = "cd_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "cd_perfil")
	private Perfil perfil;

}

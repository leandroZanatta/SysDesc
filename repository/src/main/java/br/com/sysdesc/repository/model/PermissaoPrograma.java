package br.com.sysdesc.repository.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_permissaoprograma database table.
 * 
 */
@Entity
@Table(name="tb_permissaoprograma")
@NamedQuery(name="PermissaoPrograma.findAll", query="SELECT p FROM PermissaoPrograma p")
public class PermissaoPrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_PERMISSAOPROGRAMA_IDPERMISSAOPROGRAMA_GENERATOR", sequenceName="GEN_PERMISSAOPROGRAMA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_PERMISSAOPROGRAMA_IDPERMISSAOPROGRAMA_GENERATOR")
	@Column(name="id_permissaoprograma")
	private long idPermissaoprograma;

	@Column(name="fl_cadastro")
	private Boolean flCadastro;

	@Column(name="fl_exclusao")
	private Boolean flExclusao;

	@Column(name="fl_leitura")
	private Boolean flLeitura;

	//bi-directional many-to-one association to Programa
	@ManyToOne
	@JoinColumn(name="cd_programa")
	private Programa tbPrograma;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="cd_usuario")
	private Usuario tbUsuario;

	public PermissaoPrograma() {
	}

	public long getIdPermissaoprograma() {
		return this.idPermissaoprograma;
	}

	public void setIdPermissaoprograma(long idPermissaoprograma) {
		this.idPermissaoprograma = idPermissaoprograma;
	}

	public Boolean getFlCadastro() {
		return this.flCadastro;
	}

	public void setFlCadastro(Boolean flCadastro) {
		this.flCadastro = flCadastro;
	}

	public Boolean getFlExclusao() {
		return this.flExclusao;
	}

	public void setFlExclusao(Boolean flExclusao) {
		this.flExclusao = flExclusao;
	}

	public Boolean getFlLeitura() {
		return this.flLeitura;
	}

	public void setFlLeitura(Boolean flLeitura) {
		this.flLeitura = flLeitura;
	}

	public Programa getTbPrograma() {
		return this.tbPrograma;
	}

	public void setTbPrograma(Programa tbPrograma) {
		this.tbPrograma = tbPrograma;
	}

	public Usuario getTbUsuario() {
		return this.tbUsuario;
	}

	public void setTbUsuario(Usuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

}
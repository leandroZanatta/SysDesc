package br.com.sysdesc.repository.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_cliente")
@SequenceGenerator(name = "GEN_CLIENTE", allocationSize = 1, sequenceName = "GEN_CLIENTE")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_CLIENTE")
	@Column(name = "id_cliente")
	private Long idCliente;

	@Column(name = "fl_tipocliente")
	private String flagTipoCliente;

	@Column(name = "tx_cgc")
	private String cgc;

	@Column(name = "tx_nome")
	private String nome;

	@Column(name = "dt_nascimento")
	@Temporal(TemporalType.DATE)
	private Date datadenascimento;

	@Column(name = "tx_rg")
	private String rgie;

	@ManyToOne
	@JoinColumn(name = "cd_cidade")
	private Cidade cidade;

	@Column(name = "tx_endereco")
	private String endereco;

	@Column(name = "tx_numero")
	private String numero;

	@Column(name = "tx_bairro")
	private String bairro;

	@Column(name = "tx_cep")
	private String cep;

	@Column(name = "tx_telefone")
	private String telefone;

	@Column(name = "tx_email")
	private String email;

	@Column(name = "nr_estadocivil")
	private Long estadocivil;

	@Column(name = "fl_sexo")
	private String sexo;

	@Column(name = "nr_situacao")
	private Long situacao;

	@OneToMany(mappedBy = "cliente")
	private List<Limite> limites;

	@OneToMany(mappedBy = "cliente")
	private List<Usuario> usuarios;

	@OneToMany(mappedBy = "fornecedor")
	private List<Produto> produtos;

}
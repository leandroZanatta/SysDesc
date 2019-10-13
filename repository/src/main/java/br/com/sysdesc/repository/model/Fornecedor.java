package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_fornecedor")
@SequenceGenerator(name = "GEN_FORNECEDOR", allocationSize = 1, sequenceName = "GEN_FORNECEDOR")
public class Fornecedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_FORNECEDOR")
	@Column(name = "id_fornecedor")
	private Long idfornecedor;

	@ManyToOne
	@JoinColumn(name = "cd_cliente", referencedColumnName = "id_cliente")
	private Cliente cliente;

	@Column(name = "cd_planoconta", insertable = false, updatable = false)
	private Long codigoConta;

	@ManyToOne
	@JoinColumn(name = "cd_planoconta")
	private PlanoContas planoContas;

	@Column(name = "nr_banco")
	private Long numeroBanco;

	@Column(name = "tx_agencia")
	private String Agencia;

	@Column(name = "tx_conta")
	private String Conta;

	@Column(name = "nr_tipoconta")
	private Long tipoConta;

	@Column(name = "cd_status")
	private Long codigoStatus;
}
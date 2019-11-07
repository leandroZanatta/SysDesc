package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_empresa")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_empresa")
	private Long idEmpresa;

	@Column(name = "cd_cliente")
	private Long codigoCliente;

	@Column(name = "cd_matriz")
	private Long codigoMatriz;

	@Column(name = "tx_nomefantasia")
	private String nomeFantasia;

	@Column(name = "cd_identificacao")
	private Long codigoIndentificacao;

	@Column(name = "cd_tributacao")
	private Long codigoTributacao;

	@Column(name = "fl_apuracao")
	private String apuracao;

	@Column(name = "cd_apuracaoestoque")
	private Long codigoapuracaoEstoque;

}
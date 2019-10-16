package br.com.sysdesc.upgrade.vo;

public class VersaoVO {

	private String versaoERP = "0.0.0";

	private String versaoPDV = "0.0.0";

	private String arquivoERP;

	private String arquivoGerenciador;

	private String arquivoSysdesc;

	private String arquivoREST;

	private String arquivoPDV;

	public String getVersaoERP() {
		return versaoERP;
	}

	public void setVersaoERP(String versaoERP) {
		this.versaoERP = versaoERP;
	}

	public String getVersaoPDV() {
		return versaoPDV;
	}

	public void setVersaoPDV(String versaoPDV) {
		this.versaoPDV = versaoPDV;
	}

	public String getArquivoERP() {
		return arquivoERP;
	}

	public void setArquivoERP(String arquivoERP) {
		this.arquivoERP = arquivoERP;
	}

	public String getArquivoGerenciador() {
		return arquivoGerenciador;
	}

	public void setArquivoGerenciador(String arquivoGerenciador) {
		this.arquivoGerenciador = arquivoGerenciador;
	}

	public String getArquivoSysdesc() {
		return arquivoSysdesc;
	}

	public void setArquivoSysdesc(String arquivoSysdesc) {
		this.arquivoSysdesc = arquivoSysdesc;
	}

	public String getArquivoPDV() {
		return arquivoPDV;
	}

	public void setArquivoPDV(String arquivoPDV) {
		this.arquivoPDV = arquivoPDV;
	}

	public String getArquivoREST() {
		return arquivoREST;
	}

	public void setArquivoREST(String arquivoREST) {
		this.arquivoREST = arquivoREST;
	}

}

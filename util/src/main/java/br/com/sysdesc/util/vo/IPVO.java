package br.com.sysdesc.util.vo;

public class IPVO {

	private String ip;

	private String mascara;

	private Integer porta;

	public IPVO(String ip, String mascara, Integer porta) {
		this.ip = ip;
		this.mascara = mascara;
		this.porta = porta;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	public Integer getPorta() {
		return porta;
	}

	public void setPorta(Integer porta) {
		this.porta = porta;
	}

}

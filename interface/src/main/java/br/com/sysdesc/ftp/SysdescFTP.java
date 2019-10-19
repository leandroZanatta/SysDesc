package br.com.sysdesc.ftp;

import br.com.sysdesc.util.resources.ApplicationProperies;

public class SysdescFTP {

	public static void startServer() {

		Integer porta = Integer.valueOf(ApplicationProperies.getInstance().getProperty("ftp.server.port", "3210"));

		new FtpServer(porta).start();
	}

}

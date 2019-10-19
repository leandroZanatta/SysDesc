package br.com.sysdesc.rest;

import br.com.sysdesc.http.server.JavaHTTPServer;
import br.com.sysdesc.util.resources.ApplicationProperies;

public class SysdescRest {

	public static void startServer() {

		Integer porta = Integer.valueOf(ApplicationProperies.getInstance().getProperty("erp.server.port", "3200"));

		new JavaHTTPServer(porta, "br.com.sysdesc.controller").start();
	}

}

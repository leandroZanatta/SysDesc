package br.com.sysdesc.gerenciador.inicializacao.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.sysdesc.gerenciador.inicializacao.util.ApplicationProperies;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaHTTPServer extends Thread {

	private ExecutorService executorService = Executors.newCachedThreadPool();

	@Override
	public void run() {

		ApisController.getInstance();

		Integer porta = Integer.valueOf(ApplicationProperies.getInstance().getProperty("server.port", "8090"));

		try (ServerSocket serverConnect = new ServerSocket(porta);) {

			while (true) {

				executorService.submit(new RestRequisition(serverConnect.accept()));
			}

		} catch (IOException e) {

			log.error("Não foi possivel inicializar o servidor", e);
		}
	}

}

package br.com.sysdesc.gerenciador.inicializacao.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import br.com.sysdesc.gerenciador.inicializacao.http.enumerators.HttpMethod;
import br.com.sysdesc.gerenciador.inicializacao.vo.ApiMethodVO;

public class Request {

	private HttpMethod method;
	private String fileRequested;
	private Map<String, String> headers;
	private String payload;
	private PrintWriter printWriter;
	private BufferedOutputStream bufferedOutputStream;

	public Request(HttpMethod method, String fileRequested, Map<String, String> headers, String payload,
			PrintWriter printWriter, BufferedOutputStream bufferedOutputStream) {
		this.method = method;
		this.fileRequested = fileRequested;
		this.headers = headers;
		this.payload = payload;
		this.printWriter = printWriter;
		this.bufferedOutputStream = bufferedOutputStream;
	}

	public void process() throws IOException {

		Optional<ApiMethodVO> metodo = this.findMethod();

		if (metodo.isPresent()) {

			return;
		}

		retornarNotFound();
	}

	private void retornarNotFound() throws IOException {
		String ops = " ";

		printWriter.println("HTTP/1.1 404 Not Found");
		printWriter.println("Server: Java HTTP Server");
		printWriter.println("Date: " + new Date());
		printWriter.println("Content-type: " + "application/json");
		printWriter.println("Content-length: " + ops.length());
		printWriter.println();

		bufferedOutputStream.write(ops.getBytes());

		printWriter.flush();
		bufferedOutputStream.flush();
	}

	private Optional<ApiMethodVO> findMethod() {

		if (ApisController.getInstance().getApis().containsKey(this.method)) {
			return Optional.ofNullable(ApisController.getInstance().getApis().get(this.method).get(this.fileRequested));
		}

		return Optional.empty();
	}

}

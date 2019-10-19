package br.com.sysdesc.ftp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.sysdesc.util.resources.Configuracoes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FtpServer extends Thread {

	private final Integer port;

	public FtpServer(Integer port) {
		this.port = port;
	}

	@Override
	public void run() {

		try (ServerSocket serverConnect = new ServerSocket(port);) {

			while (true) {

				Socket socket = serverConnect.accept();

				try (DataInputStream input = new DataInputStream(socket.getInputStream());
						DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

					output.writeUTF("Bem-vindo, você está conectado.");

					String arquivo = input.readUTF();

					System.out.println("Arquivo :" + arquivo);

					File file = new File(Configuracoes.FOLDER_VERSOES + Configuracoes.SEPARATOR + arquivo);

					if (file.exists()) {

						ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

						System.out.println("Transferindo o arquivo: " + file.getName());

						out.writeUTF(file.getName());

						out.writeLong(file.length());

						try (FileInputStream filein = new FileInputStream(file)) {

							byte[] buf = new byte[4096];

							while (true) {
								int len = filein.read(buf);

								if (len == -1)

									break;

								out.write(buf, 0, len);
							}
							output.writeUTF("Arquivo enviado:");
						}
					} else {
						output.writeUTF("Não existe o arquivo!");
					}

				} catch (IOException ioe) {

					log.error("Server error ", ioe);
				}

			}

		} catch (IOException e) {

			log.error("Não foi possivel inicializar o servidor", e);
		}
	}
}

package br.com.sysdesc.gerenciador.inicializacao.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class ProcessUtil {

	private ProcessUtil() {

	}

	public static Process createProcess(String comand) throws IOException {

		return createProcess(comand, null, null);
	}

	public static Process createProcess(String comand, File logFile, String path) throws IOException {

		ProcessBuilder builder = new ProcessBuilder(Arrays.asList(comand.split(" ")));

		if (logFile != null) {
			builder.inheritIO();

			builder.redirectOutput(logFile);

		}

		return builder.start();

	}
}

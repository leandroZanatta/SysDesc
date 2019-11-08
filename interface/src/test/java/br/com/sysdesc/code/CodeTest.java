package br.com.sysdesc.code;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.sysdesc.atualizacao.util.resources.Configuracoes;

@RunWith(MockitoJUnitRunner.class)
public class CodeTest {

	private List<String> classes = new ArrayList<String>();

	public CodeTest() {
		listClasses(new File(new File(Configuracoes.USER_DIR).getParent()));
	}

	@Test
	public void testarLogger() throws IOException {

		System.out.println(String.format("Validando %d classes", classes.size()));

		Boolean fail = Boolean.FALSE;

		for (String fileStr : classes) {

			List<String> conteudo = FileUtils.readLines(new File(fileStr), StandardCharsets.UTF_8);

			Optional<String> optional = conteudo.stream().filter(x -> x.contains("Logger.")).findFirst();

			if (optional.isPresent()) {

				System.out.println(
						String.format("Classe %s contém uma Chamada a Logger na linha %d alterar para anotação @slf4j",
								fileStr, conteudo.indexOf(optional.get())));

				fail = Boolean.TRUE;
			}

			List<String> logs = conteudo.stream().filter(x -> x.contains("log.")).collect(Collectors.toList());

			for (String log : logs) {

				if (log.contains("\"")) {

					System.out.println(String.format(
							"Classe %s contém uma uma variável mágica. Alterar código '%s' por constante na linha %d",
							fileStr, log.trim(), conteudo.indexOf(log)));

					fail = Boolean.TRUE;
				}
			}

		}

		if (fail) {
			Assert.fail();
		}
	}

	private void listClasses(File file) {

		if (file.isDirectory()) {

			for (File newFile : file.listFiles()) {
				listClasses(newFile);
			}
		} else {
			if (!file.getAbsolutePath().contains("test")
					&& FilenameUtils.getExtension(file.getAbsolutePath()).contains("java")) {
				classes.add(file.getAbsolutePath());
			}
		}

	}

}

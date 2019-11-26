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

				} else if (!log.contains("translate(")) {

					System.out.println(String.format(
							"Classe %s O log informado não utiliza translate. Alterar código '%s' por constante na linha %d",
							fileStr, log.trim(), conteudo.indexOf(log)));

					fail = Boolean.TRUE;

				}
			}

			List<String> labelsSemTranslate = conteudo.stream()
					.filter(x -> x.contains("new JLabel(") && !x.contains("translate(")).collect(Collectors.toList());

			for (String label : labelsSemTranslate) {

				System.out.println(
						String.format("Classe %s contém um label que não utiliza translate. Linha: %d  valor: %s",
								fileStr, conteudo.indexOf(label), label.trim()));

				fail = Boolean.TRUE;
			}

			List<String> titulosSemTranslate = conteudo.stream()
					.filter(x -> x.contains("setTitle(") && !x.contains("translate(")).collect(Collectors.toList());

			for (String titulo : titulosSemTranslate) {

				System.out.println(
						String.format("Classe %s contém um título que não utiliza translate. Linha: %d  valor: %s",
								fileStr, conteudo.indexOf(titulo), titulo.trim()));

				fail = Boolean.TRUE;
			}

			Optional<String> optionalConstrutor = conteudo.stream()
					.filter(x -> !fileStr.contains("//ui//")
							&& x.contains("public " + FilenameUtils.getBaseName(fileStr) + "(") && x.contains(") {"))
					.findFirst();

			if (optionalConstrutor.isPresent()) {

				Integer inicioConstrutor = conteudo.indexOf(optionalConstrutor.get());
				Integer finalConstrutor = conteudo.size();

				for (int i = inicioConstrutor + 1; i < conteudo.size(); i++) {

					if (conteudo.get(i).contains("{")) {

						System.out.println(String.format("Classe %s Construtor contem regra de negócio.", fileStr));

						fail = Boolean.TRUE;

						break;
					}

					if (conteudo.get(i).contains("}")) {
						finalConstrutor = i;

						break;
					}
				}

				List<String> construct = conteudo.subList(inicioConstrutor, finalConstrutor);

				if (construct.size() > 10) {

					System.out.println(String.format("Classe %s Construtor possui mais que 10 linhas.", fileStr));

					fail = Boolean.TRUE;
				}

				if (!construct.stream().anyMatch(x -> x.contains("initComponents"))) {

					System.out
							.println(String.format("Classe %s Construtor não possui método initComponents.", fileStr));

					fail = Boolean.TRUE;
				}

			}

		}

		if (fail) {
			// Assert.fail();
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

package br.com.sysdesc.upgrade.startup;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;

import br.com.sysdesc.upgrade.util.classes.ProcessUtil;

public class VersaoService {

	public String adicionarVersao(String versao) throws IOException {

		String[] arrayVersao = versao.split("\\.");

		Long novaVersao = Long.valueOf(arrayVersao[2]) + 1L;

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(arrayVersao[0]).append(".").append(arrayVersao[1]).append(".").append(novaVersao);

		return stringBuilder.toString();
	}

	public void changeMavenVersion(File pathDir, String novaVersao) throws Exception {

		InvocationRequest request = new DefaultInvocationRequest();
		Invoker invoker = new DefaultInvoker();

		request.setBaseDirectory(pathDir);
		request.setPomFile(new File(pathDir, "pom.xml"));

		request.setGoals(Collections.singletonList("versions:set -DnewVersion=" + novaVersao));

		InvocationResult result = invoker.execute(request);

		if (result.getExitCode() != 0) {
			Logger.getLogger("GerarVersao").severe("Erro ao alterar versao:");

			return;
		}

		request.setGoals(Collections.singletonList("clean install"));
		result = invoker.execute(request);

		if (result.getExitCode() != 0) {
			Logger.getLogger("GerarVersao").severe("erro ao gerar build:");

			return;
		}

	}

	public void changeReactVersion(File sysdescPDV, String novaVersao) throws Exception {

		StringBuilder stringBuilder = new StringBuilder("function getVersion() {\n");
		stringBuilder.append("	return '").append(novaVersao).append("';\n");
		stringBuilder.append("}\n\n");
		stringBuilder.append("export default Version;");

		FileUtils.write(new File(sysdescPDV, "src/version.js"), stringBuilder.toString(), Charset.defaultCharset());

		ProcessUtil.createProcess("npm.cmd run build", null, sysdescPDV.getAbsolutePath()).waitFor();
	}
}

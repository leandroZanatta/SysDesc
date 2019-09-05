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

import com.google.gson.Gson;

import br.com.sysdesc.upgrade.vo.VersaoVO;

public class GerarVersao {

	public static void main(String[] args) {

		try {

			File pathDir = new File(System.getProperty("user.dir")).getParentFile();

			File arquivoJson = new File(pathDir, "versoes\\versao.json");

			InvocationRequest request = new DefaultInvocationRequest();
			Invoker invoker = new DefaultInvoker();

			VersaoVO versaoVO = setarVersao(pathDir, arquivoJson);

			request.setBaseDirectory(pathDir);
			request.setPomFile(new File(pathDir, "pom.xml"));

			request.setGoals(Collections.singletonList("versions:set -DnewVersion=" + versaoVO.getVersao()));

			InvocationResult result = invoker.execute(request);

			if (result.getExitCode() != 0) {
				Logger.getLogger("GerarVersao").severe("Erro ao alterar versao:");

				return;
			}

			request.setGoals(Collections.singletonList("install"));
			result = invoker.execute(request);

			if (result.getExitCode() != 0) {
				Logger.getLogger("GerarVersao").severe("erro ao gerar build:");

				return;
			}

			FileUtils.writeStringToFile(arquivoJson, new Gson().toJson(versaoVO), Charset.defaultCharset());

		} catch (Exception e) {
			Logger.getLogger("GerarVersao").severe("Erro ao gerar versao:" + e.getMessage());
		}
	}

	private static VersaoVO setarVersao(File pathDir, File arquivoJson) throws IOException {

		VersaoVO versaoVO = new Gson().fromJson(FileUtils.readFileToString(arquivoJson, Charset.defaultCharset()),
				VersaoVO.class);

		String[] arrayVersao = versaoVO.getVersao().split("\\.");

		Long novaVersao = Long.valueOf(arrayVersao[2]) + 1L;

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(arrayVersao[0]).append(".").append(arrayVersao[1]).append(".").append(novaVersao);

		versaoVO.setVersao(stringBuilder.toString());

		versaoVO.setArquivo(
				"https://github.com/leandroZanatta/SysDesc/raw/develop/versoes/" + stringBuilder.toString() + ".zip");

		return versaoVO;
	}

}

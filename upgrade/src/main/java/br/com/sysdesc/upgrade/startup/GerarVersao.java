package br.com.sysdesc.upgrade.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

			request.setGoals(Collections.singletonList("clean install"));
			result = invoker.execute(request);

			if (result.getExitCode() != 0) {
				Logger.getLogger("GerarVersao").severe("erro ao gerar build:");

				return;
			}

			gerarArquivoZip(pathDir, versaoVO.getVersao());

			FileUtils.writeStringToFile(arquivoJson, new Gson().toJson(versaoVO), Charset.defaultCharset());

		} catch (Exception e) {
			Logger.getLogger("GerarVersao").severe("Erro ao gerar versao:" + e.getMessage());
		}
	}

	private static void gerarArquivoZip(File pathDir, String versao) throws Exception {

		File target = new File(pathDir, "interface\\target");

		File upgrade = new File(pathDir, "upgrade\\upgrade");

		File arquuivoZip = new File(pathDir, "versoes\\" + versao + ".zip");

		if (arquuivoZip.exists()) {
			arquuivoZip.delete();
		}

		arquuivoZip.createNewFile();

		ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(arquuivoZip));

		byte[] buffer = new byte[1024];

		zipFile("", upgrade, zipFile, buffer);

		zipFile("", new File(target, "resources"), zipFile, buffer);

		zipFile("", new File(target, "lib"), zipFile, buffer);

		zipFile("", new File(target, "interface.jar"), zipFile, buffer);

		zipFile.closeEntry();

		zipFile.close();

	}

	private static void zipFile(String path, File file, ZipOutputStream zipFile, byte[] buffer) throws IOException {

		String separator = path.length() == 0 ? "" : (path + "/");

		if (file.isDirectory()) {

			for (File entry : file.listFiles()) {

				zipFile(separator + file.getName(), entry, zipFile, buffer);
			}

			return;
		}

		zipFile.putNextEntry(new ZipEntry(separator + file.getName()));

		try (FileInputStream fis = new FileInputStream(file)) {

			int length;

			while ((length = fis.read(buffer)) > 0) {
				zipFile.write(buffer, 0, length);
			}

		}

		zipFile.closeEntry();

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

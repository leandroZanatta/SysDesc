package br.com.sysdesc.upgrade.startup;

import java.io.File;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.sysdesc.upgrade.vo.VersaoVO;

public class GerarVersaoPDV {

	private static final String URL_VERSOES = "https://github.com/leandroZanatta/SysDesc/raw/develop/versoes/";

	private String versaoPDV;

	private File pathDir = new File(System.getProperty("user.dir")).getParentFile();
	private VersaoService versaoService = new VersaoService();
	private ZipService zipService = new ZipService();

	public GerarVersaoPDV(String versaoPDV) {
		this.versaoPDV = versaoPDV;
	}

	public String build() {

		try {

			File arquivoJson = new File(pathDir, "versoes\\versao.json");

			String versao = FileUtils.readFileToString(arquivoJson, Charset.defaultCharset());

			VersaoVO versaoVO = new Gson().fromJson(versao, VersaoVO.class);
			String novaVersao = versaoService.adicionarVersao(this.versaoPDV);

			File sysdescREST = new File(pathDir, "sysdesc-rest");
			File gerenciador = new File(pathDir, "gerenciador-inicializacao");
			File sysdescPDV = new File(pathDir, "sysdescpdv");

			versaoService.changeMavenVersion(sysdescREST, novaVersao);
			versaoService.changeMavenVersion(gerenciador, novaVersao);
			versaoService.changeReactVersion(sysdescPDV, novaVersao);

			File gerenciadorDependencias = new File(gerenciador,
					"target\\gerenciador-inicializacao-jar-with-dependencies.jar");

			File restDependencias = new File(sysdescREST, "target\\sysdesc-rest-" + novaVersao + ".jar");

			File gerenciadorJar = new File(gerenciador, "target\\gerenciador-inicializacao.jar");
			File sysdescJar = new File(sysdescREST, "target\\sysdesc-rest.jar");

			gerenciadorDependencias.renameTo(gerenciadorJar);
			restDependencias.renameTo(sysdescJar);

			File desktop = new File(sysdescPDV, "desktop");
			File appAsar = new File(sysdescPDV, "app.asar");

			File newAsar = new File(desktop, "resources\\app.asar");

			if (newAsar.exists()) {
				newAsar.delete();
			}

			newAsar.createNewFile();

			FileUtils.copyFile(appAsar, newAsar);

			zipService.createZip(pathDir, "versoes\\" + novaVersao + "-gerenciador.zip", gerenciadorJar);
			zipService.createZip(pathDir, "versoes\\" + novaVersao + "-sysdesc-rest.zip", sysdescJar);
			zipService.createZip(pathDir, "versoes\\" + novaVersao + "-sysdescpdv.zip", desktop);

			versaoVO.setArquivoGerenciador(URL_VERSOES + novaVersao + "-gerenciador.zip");
			versaoVO.setArquivoREST(URL_VERSOES + novaVersao + "-sysdesc-rest.zip");
			versaoVO.setArquivoPDV(URL_VERSOES + novaVersao + "-sysdescpdv.zip");
			versaoVO.setVersaoPDV(novaVersao);

			FileUtils.writeStringToFile(arquivoJson, new Gson().toJson(versaoVO), Charset.defaultCharset());

			return novaVersao;
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar versao:" + e.getMessage());
		}

		return this.versaoPDV;
	}

}

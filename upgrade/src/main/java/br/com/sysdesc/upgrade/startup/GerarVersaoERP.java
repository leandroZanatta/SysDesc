package br.com.sysdesc.upgrade.startup;

import java.io.File;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.sysdesc.upgrade.vo.VersaoVO;

public class GerarVersaoERP {

	private static final String URL_VERSOES = "https://github.com/leandroZanatta/SysDesc/raw/develop/versoes/";

	private String versaoERP;

	private File pathDir = new File(System.getProperty("user.dir")).getParentFile();
	private VersaoService versaoService = new VersaoService();
	private ZipService zipService = new ZipService();

	public GerarVersaoERP(String versaoERP) {
		this.versaoERP = versaoERP;
	}

	public String build() {

		try {

			File arquivoJson = new File(pathDir, "versoes\\versao.json");
			File target = new File(pathDir, "interface\\target");
			File upgrade = new File(pathDir, "upgrade\\upgrade");
			File atualizacao = new File(pathDir, "atualizacao\\target\\atualizacao-jar-with-dependencies.jar");

			String versao = FileUtils.readFileToString(arquivoJson, Charset.defaultCharset());
			VersaoVO versaoVO = new Gson().fromJson(versao, VersaoVO.class);
			String novaVersao = versaoService.adicionarVersao(this.versaoERP);

			File resources = new File(target, "resources");
			File lib = new File(target, "lib");
			File interfaceJar = new File(target, "interface.jar");

			File sysdesc = new File(pathDir, "atualizacao\\target\\sysdesc.jar");
			File sysdesResources = new File(pathDir, "atualizacao\\resources");

			versaoService.changeMavenVersion(pathDir, novaVersao);

			atualizacao.renameTo(sysdesc);

			zipService.createZip(pathDir, "versoes\\" + novaVersao + "-erp.zip", resources, lib, interfaceJar, upgrade);
			zipService.createZip(pathDir, "versoes\\" + novaVersao + "-sysdesc.zip", sysdesc, sysdesResources);

			versaoVO.setArquivoERP(URL_VERSOES + novaVersao + "-erp.zip");
			versaoVO.setArquivoSysdesc(URL_VERSOES + novaVersao + "-sysdesc.zip");
			versaoVO.setVersaoERP(novaVersao);

			FileUtils.writeStringToFile(arquivoJson, new Gson().toJson(versaoVO), Charset.defaultCharset());

			return novaVersao;
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar versao:" + e.getMessage());
		}

		return this.versaoERP;
	}

}

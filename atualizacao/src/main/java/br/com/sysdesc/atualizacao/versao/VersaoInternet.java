package br.com.sysdesc.atualizacao.versao;

import static br.com.sysdesc.atualizacao.util.resources.Resources.APPLICATION_VERSOES;
import static br.com.sysdesc.atualizacao.util.resources.Resources.translate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.sysdesc.atualizacao.ui.FrmDownloader;
import br.com.sysdesc.atualizacao.util.resources.Configuracoes;
import br.com.sysdesc.atualizacao.vo.VersaoVO;
import liquibase.util.file.FilenameUtils;

public class VersaoInternet {

	private static final String URL_ARQUIVO_VERSOES = translate(APPLICATION_VERSOES);

	public File obterArquivoVersaoZip(VersaoVO versaoVO) throws IOException, MalformedURLException {

		File arquivoVersaoZip = new File(Configuracoes.PATH_VERSOES,
				FilenameUtils.getName(new URL(versaoVO.getArquivo()).getPath()));

		if (!arquivoVersaoZip.exists()) {

			createFileAndFloders(arquivoVersaoZip);

			baixarVersaoZipInternet(arquivoVersaoZip, versaoVO.getArquivo());
		}

		return arquivoVersaoZip;
	}

	private void createFileAndFloders(File arquivoVersaoZip) throws IOException {
		new File(arquivoVersaoZip.getParent()).mkdirs();

		arquivoVersaoZip.createNewFile();

	}

	public VersaoVO obterVersaoVO(File arquivoVersao) throws Exception {

		if (!arquivoVersao.exists()) {

			createFileAndFloders(arquivoVersao);

			buscarArquivoVersaoInternet(arquivoVersao);
		}

		return obterVersaoVOArquivo(arquivoVersao);
	}

	private VersaoVO obterVersaoVOArquivo(File arquivoVersao) throws IOException {

		return new Gson().fromJson(FileUtils.readFileToString(arquivoVersao, Charset.forName("UTF-8")), VersaoVO.class);
	}

	private void baixarVersaoZipInternet(File arquivoVersaoZip, String arquivo) {

		FrmDownloader frmDownloader = new FrmDownloader(arquivo, arquivoVersaoZip);

		frmDownloader.setVisible(Boolean.TRUE);

		if (!frmDownloader.isSucesso()) {

			throw new RuntimeException("Não foi possivel fazer download da versão.");
		}
	}

	private void buscarArquivoVersaoInternet(File arquivoVersao) throws Exception {

		FrmDownloader frmDownloader = new FrmDownloader(URL_ARQUIVO_VERSOES, arquivoVersao);

		frmDownloader.setVisible(Boolean.TRUE);

		if (!frmDownloader.isSucesso()) {

			throw new RuntimeException("Não foi possivel fazer download do arquivo de versões.");
		}

	}

}

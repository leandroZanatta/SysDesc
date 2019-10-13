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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VersaoInternet {

	private static final String URL_ARQUIVO_VERSOES = translate(APPLICATION_VERSOES);

	public File obterArquivoVersaoZip(VersaoVO versaoVO) throws IOException, MalformedURLException {
		log.info("Buscando arquivo de configurações");

		File arquivoVersaoZip = new File(Configuracoes.PATH_VERSOES,
				FilenameUtils.getName(new URL(versaoVO.getArquivoERP()).getPath()));

		if (!arquivoVersaoZip.exists()) {
			log.info("Arquivo de configuração não encontrado localmente");

			createFloders(arquivoVersaoZip);

			baixarVersaoZipInternet(arquivoVersaoZip, versaoVO.getArquivoERP());
		}

		return arquivoVersaoZip;
	}

	private void createFloders(File arquivoVersaoZip) throws IOException {
		new File(arquivoVersaoZip.getParent()).mkdirs();
	}

	public VersaoVO obterVersaoVO(File arquivoVersao) throws Exception {

		if (!arquivoVersao.exists()) {
			log.info("Arquivo de versão inexistente - Criando arquivos");

			createFloders(arquivoVersao);

			buscarArquivoVersaoInternet(arquivoVersao);
		}

		log.info("Carregando arquivo" + arquivoVersao);

		return obterVersaoVOArquivo(arquivoVersao);
	}

	private VersaoVO obterVersaoVOArquivo(File arquivoVersao) throws IOException {

		return new Gson().fromJson(FileUtils.readFileToString(arquivoVersao, Charset.forName("UTF-8")), VersaoVO.class);
	}

	private void baixarVersaoZipInternet(File arquivoVersaoZip, String arquivo) {
		log.info("Baixando Versão da internet");

		FrmDownloader frmDownloader = new FrmDownloader(arquivo, arquivoVersaoZip);

		frmDownloader.setVisible(Boolean.TRUE);

		if (!frmDownloader.isSucesso()) {

			throw new RuntimeException("Não foi possivel fazer download da versão.");
		}

		log.info("Arquivo de versão baixado com sucesso");
	}

	private void buscarArquivoVersaoInternet(File arquivoVersao) throws Exception {

		FrmDownloader frmDownloader = new FrmDownloader(URL_ARQUIVO_VERSOES, arquivoVersao);

		frmDownloader.setVisible(Boolean.TRUE);

		if (!frmDownloader.isSucesso()) {

			throw new RuntimeException("Não foi possivel fazer download do arquivo de versões.");
		}

	}

}

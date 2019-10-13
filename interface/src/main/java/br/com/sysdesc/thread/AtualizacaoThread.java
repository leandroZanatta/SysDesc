package br.com.sysdesc.thread;

import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_ATUALIZAR_VERSAO;
import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_THREAD_VERSAO_INTEROMPIDA;
import static br.com.sysdesc.util.resources.Configuracoes.FOLDER_VERSOES;
import static br.com.sysdesc.util.resources.Configuracoes.VERSAO;
import static br.com.sysdesc.util.resources.Resources.OPTION_VALIDACAO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.sysdesc.repository.dao.VersaoDAO;
import br.com.sysdesc.repository.dao.VersaoPDVDAO;
import br.com.sysdesc.repository.model.Versao;
import br.com.sysdesc.repository.model.VersaoPDV;
import br.com.sysdesc.util.vo.VersaoVO;
import liquibase.util.file.FilenameUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtualizacaoThread extends Thread {

	private JPanel contentVersao;

	private JLabel lbVersao;

	private String versaoBase;

	private final String URLVersao = "https://raw.githubusercontent.com/leandroZanatta/SysDesc/develop/versoes/versao.json";

	private VersaoDAO versaoDAO = new VersaoDAO();

	private VersaoPDVDAO versaoPDVDAO = new VersaoPDVDAO();

	public AtualizacaoThread(JPanel contentVersao) {
		this.contentVersao = contentVersao;

		lbVersao = new JLabel();
	}

	@Override
	public void run() {

		VersaoVO versaoVO = this.recuperarVersaoInternet();

		this.verificarVersaoBase(versaoVO);

		this.verificarVersaoRemota(versaoVO);

	}

	private VersaoVO recuperarVersaoInternet() {

		URL arquivoUrl;

		try {

			arquivoUrl = new URL(this.URLVersao);

			URLConnection urlConnection = arquivoUrl.openConnection();
			urlConnection.setUseCaches(Boolean.FALSE);

			try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {

				String inputLine;

				StringBuilder stringBuilder = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {

					stringBuilder.append(inputLine);
				}

				return new Gson().fromJson(stringBuilder.toString(), VersaoVO.class);

			}
		} catch (IOException e) {

			try {
				Thread.sleep(18000);

				return recuperarVersaoInternet();

			} catch (InterruptedException e1) {

				log.error(MENSAGEM_THREAD_VERSAO_INTEROMPIDA);

				return null;
			}
		}

	}

	private void verificarVersaoRemota(VersaoVO versaoVO) {

		try {

			VersaoVO versaoLocal = getArquivoVersaoLocal();

			if (!versaoBase.equals(versaoVO.getVersaoERP())) {

				String mensagem = String.format(translate(MENSAGEM_ATUALIZAR_VERSAO), versaoVO.getVersaoERP());

				Integer retornoOpcao = JOptionPane.showConfirmDialog(null, mensagem, OPTION_VALIDACAO,
						JOptionPane.YES_NO_OPTION);

				if (retornoOpcao == JOptionPane.YES_OPTION) {

					efetuarDownloadVersao(versaoVO.getArquivoERP());

					versaoLocal.setVersaoERP(versaoVO.getVersaoERP());
					versaoLocal.setArquivoERP(versaoVO.getArquivoERP());
				}
			}

			VersaoPDV versaoPDV = getVersaoPDV();

			if (!versaoVO.getVersaoPDV().equals(versaoPDV.getVersaoPDV())) {

				efetuarDownloadVersao(versaoVO.getArquivoPDV());
				efetuarDownloadVersao(versaoVO.getArquivoREST());
				efetuarDownloadVersao(versaoVO.getArquivoGerenciador());

				versaoLocal.setVersaoPDV(versaoVO.getVersaoPDV());
				versaoLocal.setArquivoGerenciador(versaoVO.getArquivoGerenciador());
				versaoLocal.setArquivoPDV(versaoVO.getArquivoPDV());
				versaoLocal.setArquivoREST(versaoVO.getArquivoREST());

				VersaoPDV novaVersao = new VersaoPDV();
				novaVersao.setDataAtualizacao(new Date());
				novaVersao.setVersaoGerenciador(versaoVO.getArquivoGerenciador());
				novaVersao.setVersaoPDV(versaoVO.getVersaoPDV());

				versaoPDVDAO.salvar(novaVersao);
			}

			FileUtils.writeStringToFile(new File(VERSAO), new Gson().toJson(versaoLocal), Charset.defaultCharset());

		} catch (IOException e) {

			try {
				Thread.sleep(18000);

				verificarVersaoRemota(versaoVO);

			} catch (InterruptedException e1) {

				log.error(MENSAGEM_THREAD_VERSAO_INTEROMPIDA);

			}
		}

	}

	private VersaoPDV getVersaoPDV() {

		try {

			return versaoPDVDAO.last();
		} catch (Exception e) {

			return new VersaoPDV();
		}
	}

	private VersaoVO getArquivoVersaoLocal() {
		try {

			return new Gson().fromJson(FileUtils.readFileToString(new File(VERSAO), Charset.defaultCharset()),
					VersaoVO.class);
		} catch (Exception e) {

			return new VersaoVO();
		}

	}

	private void efetuarDownloadVersao(String urlVersao) throws IOException {

		JProgressBar progres = new JProgressBar();

		progres.setStringPainted(true);

		contentVersao.removeAll();

		contentVersao.add(progres);

		URL arquivoUrl = new URL(urlVersao);

		URLConnection urlConnection = arquivoUrl.openConnection();

		urlConnection.setUseCaches(Boolean.FALSE);

		Long tamanhoArquivo = urlConnection.getContentLengthLong();

		File arquivoVersao = criarArquivoVersao(urlVersao);

		try (BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
				FileOutputStream fileOutputStream = new FileOutputStream(arquivoVersao)) {

			byte dataBuffer[] = new byte[1024];

			int bytesRead;

			Long bufferTotal = 0L;

			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {

				bufferTotal += bytesRead;

				fileOutputStream.write(dataBuffer, 0, bytesRead);

				progres.setValue(
						Double.valueOf(bufferTotal.doubleValue() / tamanhoArquivo.doubleValue() * 100).intValue());

			}
		}

		contentVersao.removeAll();

		contentVersao.add(lbVersao);
	}

	private File criarArquivoVersao(String arquivo) throws IOException {
		log.info("criando arquivo Versão");

		File folderVersao = new File(FOLDER_VERSOES);

		if (!folderVersao.exists()) {
			log.info("gerando arquivo:" + folderVersao.getName());
			folderVersao.mkdir();
		}

		File arquivoVersao = new File(folderVersao, FilenameUtils.getName(new URL(arquivo).getPath()));

		if (!arquivoVersao.exists()) {
			log.info("gerando arquivo:" + arquivoVersao.getName());
			arquivoVersao.createNewFile();
		}

		return arquivoVersao;
	}

	private void verificarVersaoBase(VersaoVO versaoVO) {

		Versao versao = versaoDAO.last();

		contentVersao.add(lbVersao);

		if (versao != null) {

			this.versaoBase = versao.getVersao();

			lbVersao.setText(this.versaoBase);

			return;
		}

		this.versaoBase = "0.0.0";
	}

}

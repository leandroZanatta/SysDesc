package br.com.sysdesc.thread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.google.gson.Gson;

import br.com.sysdesc.repository.dao.VersaoDAO;
import br.com.sysdesc.repository.model.Versao;
import br.com.sysdesc.util.classes.ListUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.resources.Configuracoes;
import br.com.sysdesc.util.resources.Resources;
import br.com.sysdesc.util.vo.VersaoVO;
import liquibase.util.file.FilenameUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtualizacaoThread extends Thread {

	private JPanel contentVersao;

	private JLabel lbVersao;

	private Long versaoBase;

	private final String URLVersao = "https://raw.githubusercontent.com/leandroZanatta/SysDesc/develop/versoes/versao.json";

	private VersaoDAO versaoDAO = new VersaoDAO();

	public AtualizacaoThread(JPanel contentVersao) {
		this.contentVersao = contentVersao;

		lbVersao = new JLabel();
	}

	@Override
	public void run() {

		this.verificarVersaoBase();

		this.verificarVersaoRemota();
	}

	private void verificarVersaoRemota() {

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

				in.close();

				VersaoVO versaoVO = new Gson().fromJson(stringBuilder.toString(), VersaoVO.class);

				if (!versaoBase.equals(versaoVO.getVersao())) {

					efetuarDownloadVersao(versaoVO);
				}

			}

		} catch (IOException e) {

			try {
				Thread.sleep(60000);

				verificarVersaoRemota();

			} catch (InterruptedException e1) {

				log.error(MensagemConstants.MENSAGEM_THREAD_VERSAO_INTEROMPIDA);
			}
		}

	}

	private void efetuarDownloadVersao(VersaoVO versaoVO) throws IOException {

		Integer retornoOpcao = JOptionPane
				.showConfirmDialog(null,
						String.format(Resources.translate(MensagemConstants.MENSAGEM_ATUALIZAR_VERSAO),
								formatarVersao(versaoVO.getVersao())),
						Resources.OPTION_VALIDACAO, JOptionPane.YES_NO_OPTION);

		if (retornoOpcao == JOptionPane.YES_OPTION) {

			JProgressBar progres = new JProgressBar();

			progres.setStringPainted(true);

			contentVersao.removeAll();

			contentVersao.add(progres);

			URL arquivoUrl = new URL(versaoVO.getArquivo());

			URLConnection urlConnection = arquivoUrl.openConnection();

			urlConnection.setUseCaches(Boolean.FALSE);

			Long tamanhoArquivo = urlConnection.getContentLengthLong();

			File arquivoVersao = criarArquivoVersao(versaoVO);

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

	}

	private File criarArquivoVersao(VersaoVO versaoVO) throws IOException {

		File folderVersao = new File(Configuracoes.FOLDER_VERSOES);

		if (!folderVersao.exists()) {
			folderVersao.mkdir();
		}

		File arquivoVersao = new File(FilenameUtils.getName(new URL(versaoVO.getArquivo()).getPath()));

		if (arquivoVersao.exists()) {
			arquivoVersao.createNewFile();
		}

		return arquivoVersao;
	}

	private String formatarVersao(Long codigoVesao) {

		List<String> versaoSemFormatacao = ListUtil.toList(codigoVesao.toString().split(""));

		if (versaoSemFormatacao.size() > 3) {
			versaoSemFormatacao.add(versaoSemFormatacao.size() - 3, ".");
		}
		if (versaoSemFormatacao.size() > 6) {
			versaoSemFormatacao.add(versaoSemFormatacao.size() - 6, ".");
		}
		if (versaoSemFormatacao.size() > 9) {
			versaoSemFormatacao.add(versaoSemFormatacao.size() - 9, ".");
		}

		return versaoSemFormatacao.stream().collect(Collectors.joining());

	}

	private void verificarVersaoBase() {

		Versao versao = versaoDAO.last();

		contentVersao.add(lbVersao);

		if (versao != null) {
			this.versaoBase = versao.getVersao();

			lbVersao.setText(String.format("Versão: %s", formatarVersao(this.versaoBase)));

			return;
		}

		this.versaoBase = 0L;

	}

}

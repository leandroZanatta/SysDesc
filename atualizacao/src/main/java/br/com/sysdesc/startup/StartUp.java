package br.com.sysdesc.startup;

import static br.com.sysdesc.util.resources.Resources.APPLICATION_JAR;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;

import javax.naming.ConfigurationException;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.sysdesc.changelog.core.Changelog;
import br.com.sysdesc.changelog.core.Conexao;
import br.com.sysdesc.ui.FrmConexao;
import br.com.sysdesc.ui.FrmDownloader;
import br.com.sysdesc.util.classes.LookAndFeelUtil;
import br.com.sysdesc.vo.VersaoVO;

public class StartUp {

	private Connection criarConnection() throws Exception {

		try {

			return Conexao.buscarConexao();

		} catch (ConfigurationException e) {

			LookAndFeelUtil.configureLayout();

			new FrmConexao().setVisible(Boolean.TRUE);

			return criarConnection();
		}

	}

	private void iniciarAplicacao() throws IOException, InterruptedException {
		Desktop.getDesktop().open(new File(translate(APPLICATION_JAR, "interface.jar")));
	}

	private void atualizarAplicacao(Connection connection, File arquivoVersao) {
// modificar os Jars e o arquivo upgrade.

		Changelog.runChangelog(connection);
	}

	private boolean versaoValida(Connection connection, File arquivoVersao) throws Exception {

		Long versaoBanco = this.buscarVersaoBanco(connection);

		Long versaoInternet = this.buscarVersaoInternet(arquivoVersao);

		return versaoBanco.equals(versaoInternet);
	}

	private Long buscarVersaoInternet(File arquivoVersao) throws Exception {

		FrmDownloader frmDownloader = new FrmDownloader(
				"https://raw.githubusercontent.com/leandroZanatta/SysDesc/develop/versoes/versao.json", arquivoVersao);

		frmDownloader.setVisible(Boolean.TRUE);

		VersaoVO versaoVO = new Gson().fromJson(FileUtils.readFileToString(arquivoVersao, Charset.forName("UTF-8")),
				VersaoVO.class);

		return versaoVO.getVersao();
	}

	private Long buscarVersaoBanco(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws Exception {

		StartUp startUp = new StartUp();

		Connection connection = startUp.criarConnection();

		File arquivoVersao = File.createTempFile("versao", ".json");

		if (!startUp.versaoValida(connection, arquivoVersao)) {

			startUp.atualizarAplicacao(connection, arquivoVersao);

		}

		startUp.iniciarAplicacao();

		System.exit(0);

	}

}

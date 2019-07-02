package br.com.sysdesc.atualizacao.startup;

import static br.com.sysdesc.atualizacao.util.resources.Resources.APPLICATION_JAR;
import static br.com.sysdesc.atualizacao.util.resources.Resources.translate;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.naming.ConfigurationException;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.sysdesc.atualizacao.changelog.core.Changelog;
import br.com.sysdesc.atualizacao.changelog.core.Conexao;
import br.com.sysdesc.atualizacao.ui.FrmConexao;
import br.com.sysdesc.atualizacao.ui.FrmDownloader;
import br.com.sysdesc.atualizacao.util.classes.LookAndFeelUtil;
import br.com.sysdesc.atualizacao.util.resources.Configuracoes;
import br.com.sysdesc.atualizacao.vo.VersaoVO;
import liquibase.util.file.FilenameUtils;

public class StartUp {

	private final Logger logger = Logger.getLogger(StartUp.class.getName());

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

	private void atualizarAplicacao(Connection connection, File arquivoVersao) throws Exception {

		VersaoVO versaoVO = new Gson().fromJson(FileUtils.readFileToString(arquivoVersao, Charset.forName("UTF-8")),
				VersaoVO.class);

		File arquivoVersaoZIP = criarDiretorioVersoes(FilenameUtils.getName(new URL(versaoVO.getArquivo()).getPath()));

		FrmDownloader frmDownloader = new FrmDownloader(versaoVO.getArquivo(), arquivoVersaoZIP);

		frmDownloader.setVisible(Boolean.TRUE);

		if (frmDownloader.isSucesso()) {

			this.extrairVersao(arquivoVersaoZIP);

			Changelog.runChangelog(connection);

			this.atualizarVersao(connection, versaoVO.getVersao());
		}
	}

	private void extrairVersao(File arquivoVersaoZIP) throws Exception {

		BufferedOutputStream dest = null;

		try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(arquivoVersaoZIP)))) {

			ZipEntry entry;

			while ((entry = zis.getNextEntry()) != null) {

				System.out.println("Extraindo: " + entry.getName());

				if (entry.isDirectory()) {

					new File(Configuracoes.USER_DIR + "/" + entry.getName()).mkdirs();

					continue;
				} else {

					int di = entry.getName().lastIndexOf('/');

					if (di != -1) {
						new File(Configuracoes.USER_DIR + "/" + entry.getName().substring(0, di)).mkdirs();
					}
				}

				int count;

				byte data[] = new byte[1024];

				FileOutputStream fos = new FileOutputStream(Configuracoes.USER_DIR + "/" + entry.getName());

				dest = new BufferedOutputStream(fos);

				while ((count = zis.read(data)) != -1)
					dest.write(data, 0, count);

				dest.flush();

				dest.close();
			}
		}

		arquivoVersaoZIP.delete();

	}

	private File criarDiretorioVersoes(String filename) throws IOException {

		File pathVersoes = new File("versoes");

		if (!pathVersoes.exists()) {

			pathVersoes.mkdirs();
		}

		File arquivoVersaoRar = new File(pathVersoes, filename);

		arquivoVersaoRar.createNewFile();

		return arquivoVersaoRar;
	}

	private void atualizarVersao(Connection connection, Long versao) throws IOException {

		String sqlQuery = "update tb_versao set nr_versao=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

			preparedStatement.setLong(1, versao);

			preparedStatement.executeUpdate();

			connection.commit();
		} catch (Exception e) {

			logger.log(Level.SEVERE, "Erro ao atualizar versão no banco de dados", e);
		}
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

		String sqlQuery = "select nr_versao from tb_versao";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
				ResultSet rs = preparedStatement.executeQuery()) {

			if (rs.next()) {
				return rs.getLong(1);
			}

		} catch (Exception e) {

			logger.log(Level.SEVERE, "Erro ao buscar versão no banco de dados", e);
		}

		return 0L;
	}

	public static void main(String[] args) throws Exception {

		StartUp startUp = new StartUp();

		Connection connection = startUp.criarConnection();

		File arquivoVersao = File.createTempFile("versao", ".json");

		if (!startUp.versaoValida(connection, arquivoVersao)) {

			startUp.atualizarAplicacao(connection, arquivoVersao);

		}

		arquivoVersao.delete();

		connection.close();

		startUp.iniciarAplicacao();

		System.exit(0);

	}

}

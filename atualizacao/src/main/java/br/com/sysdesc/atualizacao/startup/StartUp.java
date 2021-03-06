package br.com.sysdesc.atualizacao.startup;

import static br.com.sysdesc.atualizacao.util.resources.Resources.APPLICATION_JAR;
import static br.com.sysdesc.atualizacao.util.resources.Resources.translate;

import java.awt.Desktop;
import java.io.File;

import javax.swing.JOptionPane;

import br.com.sysdesc.atualizacao.util.resources.Configuracoes;
import br.com.sysdesc.atualizacao.versao.ExtratorZip;
import br.com.sysdesc.atualizacao.versao.VersaoBancoDados;
import br.com.sysdesc.atualizacao.versao.VersaoInternet;
import br.com.sysdesc.atualizacao.vo.VersaoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartUp {

	private final VersaoBancoDados versaoBancoDados = new VersaoBancoDados();

	private final VersaoInternet versaoInternet = new VersaoInternet();

	private final ExtratorZip extratorZip = new ExtratorZip();

	public static void main(String[] args) throws Exception {

		StartUp startUp = new StartUp();

		File arquivoVersao = new File(Configuracoes.VERSAO);

		startUp.escolherAtualizacao(arquivoVersao);

		startUp.iniciarAplicacao();

		System.exit(0);

	}

	private void escolherAtualizacao(File arquivoVersao) throws Exception {

		VersaoVO versaoVO = versaoInternet.obterVersaoVO(arquivoVersao);

		String versaoBanco = versaoBancoDados.buscarVersaoBanco();

		if (!versaoBanco.equals(versaoVO.getVersaoERP())) {

			File arquivoZip = versaoInternet.obterArquivoVersaoZip(versaoVO);

			extratorZip.extrairVersao(arquivoZip);

			versaoBancoDados.upgradeDatabase(versaoVO.getVersaoERP());

			if (arquivoZip.exists()) {
				arquivoZip.delete();
			}
		}

	}

	private void iniciarAplicacao() {

		try {

			Desktop.getDesktop().open(new File(translate(APPLICATION_JAR, "interface.jar")));

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "ERRO AO INICIALIZAR APLICAÇÃO");

			log.error("ERRO AO INICIALIZAR APLICAÇÃO", e);
		}
	}

}

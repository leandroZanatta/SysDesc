package br.com.sysdesc.upgrade.ui;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.sysdesc.upgrade.changelog.core.Changelog;
import br.com.sysdesc.upgrade.changelog.core.Conexao;
import br.com.sysdesc.upgrade.startup.GerarVersaoERP;
import br.com.sysdesc.upgrade.startup.GerarVersaoPDV;
import br.com.sysdesc.upgrade.util.classes.LookAndFeelUtil;
import br.com.sysdesc.upgrade.vo.VersaoVO;

public class FrmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lbVersaoERP;
	private JLabel lbVersaoFront;
	private JLabel lbConfiguracao;

	private JTextField txConfiguracao;
	private JTextField txVersaoErp;
	private JTextField txVersaoFront;
	private JButton btAtualizarBt;
	private JButton btGerarVersao;
	private JButton btnGerarPdv;
	private JButton btUpgade;

	public FrmPrincipal() {

		setTitle("Ações Projeto");
		getContentPane().setLayout(null);
		setSize(444, 274);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		lbConfiguracao = new JLabel("Arquivo de Configuração:");
		lbVersaoERP = new JLabel("Versão ERP:");
		lbVersaoFront = new JLabel("Versão PDV:");

		txConfiguracao = new JTextField();
		txVersaoErp = new JTextField();
		txVersaoFront = new JTextField();

		btGerarVersao = new JButton("Gerar ERP");
		btAtualizarBt = new JButton("Atualizar DB");
		btnGerarPdv = new JButton("Gerar PDV");
		btUpgade = new JButton("Upgade");

		btAtualizarBt.addActionListener(e -> new FrmConexao(txConfiguracao.getText()).setVisible(true));
		btUpgade.addActionListener(e -> executarUpgade());
		btGerarVersao.addActionListener(e -> atualizarVersaoERP());
		btnGerarPdv.addActionListener(e -> atualizarVersaoPDV());

		VersaoVO versaoVO = buscarVersoes();

		txConfiguracao.setText(System.getProperty("user.dir") + "\\interface\\config\\config.01");
		txVersaoErp.setText(versaoVO.getVersaoERP());
		txVersaoFront.setText(versaoVO.getVersaoPDV());

		btAtualizarBt.setBounds(103, 80, 104, 34);
		txConfiguracao.setBounds(10, 29, 408, 20);
		txVersaoErp.setBounds(10, 159, 185, 20);
		lbVersaoERP.setBounds(10, 142, 130, 14);
		txVersaoFront.setBounds(233, 159, 185, 20);
		lbVersaoFront.setBounds(233, 142, 128, 14);
		btGerarVersao.setBounds(36, 190, 104, 34);
		lbConfiguracao.setBounds(10, 11, 145, 14);
		btnGerarPdv.setBounds(277, 190, 104, 34);
		btUpgade.setBounds(223, 80, 104, 34);

		getContentPane().add(btAtualizarBt);
		getContentPane().add(txConfiguracao);
		getContentPane().add(txVersaoErp);
		getContentPane().add(lbVersaoERP);
		getContentPane().add(txVersaoFront);
		getContentPane().add(lbVersaoFront);
		getContentPane().add(btGerarVersao);
		getContentPane().add(lbConfiguracao);
		getContentPane().add(btnGerarPdv);
		getContentPane().add(btUpgade);
	}

	private void atualizarVersaoPDV() {

		txVersaoFront.setText(new GerarVersaoPDV(txVersaoFront.getText()).build());
	}

	private void atualizarVersaoERP() {

		txVersaoErp.setText(new GerarVersaoERP(txVersaoErp.getText()).build());
	}

	private void executarUpgade() {

		try {

			Changelog.runChangelog(Conexao.buscarConexao(txConfiguracao.getText()));

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private VersaoVO buscarVersoes() {

		try {

			File pathDir = new File(System.getProperty("user.dir")).getParentFile();

			String arquivoJson = FileUtils.readFileToString(new File(pathDir, "versoes\\versao.json"),
					Charset.defaultCharset());

			return new Gson().fromJson(arquivoJson, VersaoVO.class);

		} catch (IOException e) {

			return new VersaoVO();
		}
	}

	public static void main(String[] args) throws Exception {

		LookAndFeelUtil.configureLayout("Times New Roman plain 11");

		new FrmPrincipal().setVisible(true);
	}
}

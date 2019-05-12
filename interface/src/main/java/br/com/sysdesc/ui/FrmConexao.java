package br.com.sysdesc.ui;

import static br.com.sysdesc.util.classes.CryptoUtil.toBlowfish;
import static br.com.sysdesc.util.classes.StringUtil.CHARSET;
import static br.com.sysdesc.util.classes.StringUtil.STRING_VAZIA;
import static br.com.sysdesc.util.classes.StringUtil.arrayToString;
import static br.com.sysdesc.util.classes.StringUtil.isNullOrEmpty;
import static br.com.sysdesc.util.constants.MensagemConstants.ERRO;
import static br.com.sysdesc.util.resources.Resources.CONEXAO_INVALIDA;
import static br.com.sysdesc.util.resources.Resources.DRIVER_NAO_ENCONTRADO;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_BT_CANCELAR;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_BT_SALVAR;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_LB_BANCO;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_LB_PORTA;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_LB_SENHA;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_LB_TIPOBANCO;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_LB_URL;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_LB_USUARIO;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_MSG_SALVAR;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_PRP_CONEXAO;
import static br.com.sysdesc.util.resources.Resources.FRMCONEXAO_TITULO;
import static br.com.sysdesc.util.resources.Resources.translate;
import static java.sql.DriverManager.getConnection;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.io.FileUtils;

import br.com.sysdesc.repository.enumeradores.TipoConexaoEnum;
import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.resources.Configuracoes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrmConexao extends JDialog {

	private static final String BARRA = "/";
	private static final String SEARCH_PNG = "search.png";
	private static final String DOIS_PONTOS = ":";

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblBancoDeDados;
	private JLabel lblUrl;
	private JLabel lblPorta;
	private JLabel lblUsuario;
	private JLabel lblSenha;
	private JLabel lblBanco;
	private JPanel panel;
	private JTextField txUrl;
	private JTextField txPorta;
	private JTextField txUsuario;
	private JPasswordField txSenha;
	private JComboBox<TipoConexaoEnum> cbTipoBanco;
	private JComboBox<String> cbBanco;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnPesquisa;

	public FrmConexao() {
		initComponents();
	}

	private void initComponents() {
		setModal(Boolean.TRUE);
		setSize(360, 255);
		setUndecorated(Boolean.TRUE);
		getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();

		lblBancoDeDados = new JLabel(translate(FRMCONEXAO_LB_TIPOBANCO));
		lblUrl = new JLabel(translate(FRMCONEXAO_LB_URL));
		lblPorta = new JLabel(translate(FRMCONEXAO_LB_PORTA));
		lblUsuario = new JLabel(translate(FRMCONEXAO_LB_USUARIO));
		lblSenha = new JLabel(translate(FRMCONEXAO_LB_SENHA));
		lblBanco = new JLabel(translate(FRMCONEXAO_LB_BANCO));

		cbTipoBanco = new JComboBox<>(TipoConexaoEnum.values());
		cbBanco = new JComboBox<>();
		txUrl = new JTextField();
		txPorta = new JTextField();
		txUsuario = new JTextField();
		txSenha = new JPasswordField();

		btnSalvar = new JButton(translate(FRMCONEXAO_BT_SALVAR));
		btnCancelar = new JButton(translate(FRMCONEXAO_BT_CANCELAR));
		btnPesquisa = new JButton("");

		cbTipoBanco.addItemListener(e -> selecionouBanco());
		cbBanco.addItemListener(e -> btnSalvar.setEnabled(cbBanco.getSelectedIndex() >= 0));
		btnPesquisa.addActionListener(e -> buscarBancosRegistrados());
		btnSalvar.addActionListener(e -> salvarConfiguracao());
		btnCancelar.addActionListener(e -> System.exit(0));

		panel.setBounds(10, 211, 340, 33);

		lblUrl.setBounds(10, 70, 46, 14);
		lblBancoDeDados.setBounds(10, 25, 81, 15);
		lblPorta.setBounds(280, 70, 46, 14);
		lblUsuario.setBounds(10, 115, 46, 14);
		lblSenha.setBounds(191, 115, 46, 14);
		lblBanco.setBounds(10, 165, 46, 14);

		cbTipoBanco.setBounds(10, 40, 340, 20);
		cbBanco.setBounds(10, 180, 309, 20);
		txUrl.setBounds(10, 86, 260, 20);
		txPorta.setBounds(280, 86, 70, 20);
		txUsuario.setBounds(10, 131, 160, 20);
		txSenha.setBounds(191, 131, 159, 20);
		btnPesquisa.setBounds(324, 179, 26, 23);

		contentPanel.add(panel);
		contentPanel.add(lblPorta);
		contentPanel.add(lblBancoDeDados);
		contentPanel.add(lblUrl);
		contentPanel.add(lblSenha);
		contentPanel.add(lblBanco);
		contentPanel.add(lblUsuario);

		contentPanel.add(cbTipoBanco);
		contentPanel.add(cbBanco);
		contentPanel.add(txUrl);
		contentPanel.add(txPorta);
		contentPanel.add(txSenha);
		contentPanel.add(txUsuario);
		contentPanel.add(btnPesquisa);

		panel.add(btnSalvar);
		panel.add(btnCancelar);

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		cbTipoBanco.setSelectedIndex(-1);
		txUrl.setEnabled(false);
		txPorta.setEnabled(false);
		txUsuario.setEnabled(false);
		txSenha.setEnabled(false);
		btnSalvar.setEnabled(false);
		btnPesquisa.setIcon(ImageUtil.resize(SEARCH_PNG, 15, 15));

		contentPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				translate(FRMCONEXAO_TITULO), TitledBorder.CENTER, TitledBorder.TOP, null, null));
		contentPanel.setLayout(null);

		setLocationRelativeTo(null);
	}

	private void salvarConfiguracao() {

		TipoConexaoEnum tipoConexaoEnum = (TipoConexaoEnum) cbTipoBanco.getSelectedItem();

		String url = txUrl.getText() + DOIS_PONTOS + txPorta.getText() + BARRA + cbBanco.getSelectedItem().toString();

		Properties properties = new Properties();
		properties.put(tipoConexaoEnum.getJdbcDriver(), tipoConexaoEnum.getDriver());
		properties.put(tipoConexaoEnum.getJdbcUrl(), url);
		properties.put(tipoConexaoEnum.getJdbcUser(), txUsuario.getText());
		properties.put(tipoConexaoEnum.getJdbcPassword(), arrayToString(txSenha.getPassword()));

		StringWriter writer = new StringWriter();

		try {

			properties.store(writer, translate(FRMCONEXAO_PRP_CONEXAO));

			FileUtils.writeStringToFile(new File(Configuracoes.CONEXAO), toBlowfish(writer.toString()), CHARSET);

			dispose();

		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, translate(FRMCONEXAO_MSG_SALVAR));
		}
	}

	private void buscarBancosRegistrados() {

		if (!(isNullOrEmpty(txUrl.getText()) || isNullOrEmpty(txPorta.getText()) || isNullOrEmpty(txUsuario.getText())
				|| isNullOrEmpty(arrayToString(txSenha.getPassword())))) {

			TipoConexaoEnum tipoConexaoEnum = (TipoConexaoEnum) cbTipoBanco.getSelectedItem();

			String urlConexao = txUrl.getText() + DOIS_PONTOS + txPorta.getText() + BARRA
					+ tipoConexaoEnum.getDefaultDatabase();

			try {

				Class.forName(tipoConexaoEnum.getDriver());

				this.criarConexao(tipoConexaoEnum, urlConexao);

			} catch (Exception e) {

				JOptionPane.showMessageDialog(this, translate(DRIVER_NAO_ENCONTRADO), ERRO, JOptionPane.ERROR_MESSAGE);

				log.error(translate(DRIVER_NAO_ENCONTRADO), e);
			}
		}
	}

	private void criarConexao(TipoConexaoEnum tipoConexaoEnum, String urlConexao) {

		try (

				Connection conn = getConnection(urlConexao, txUsuario.getText(), arrayToString(txSenha.getPassword()));

				PreparedStatement st = conn.prepareStatement(tipoConexaoEnum.getDatabase());

				ResultSet rs = st.executeQuery()) {

			cbBanco.removeAllItems();

			while (rs.next()) {
				cbBanco.addItem(rs.getString(1));
			}
		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, translate(CONEXAO_INVALIDA), ERRO, JOptionPane.ERROR_MESSAGE);

			log.error(translate(CONEXAO_INVALIDA), e);
		}
	}

	private void selecionouBanco() {

		String url = STRING_VAZIA;
		String porta = STRING_VAZIA;
		Boolean selecionou = cbTipoBanco.getSelectedIndex() >= 0;

		if (selecionou) {
			TipoConexaoEnum tipoConexaoEnum = (TipoConexaoEnum) cbTipoBanco.getSelectedItem();
			url = tipoConexaoEnum.getUrl();
			porta = tipoConexaoEnum.getPorta().toString();
		}

		txUrl.setEnabled(selecionou);
		txPorta.setEnabled(selecionou);
		txUsuario.setEnabled(selecionou);
		txSenha.setEnabled(selecionou);

		txUrl.setText(url);
		txPorta.setText(porta);
		cbBanco.removeAllItems();
	}

}

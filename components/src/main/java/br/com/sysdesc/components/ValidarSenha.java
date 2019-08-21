package br.com.sysdesc.components;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import br.com.sysdesc.util.classes.CryptoUtil;
import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.classes.StringUtil;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class ValidarSenha extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btConfirmar;
	private JButton btCancelar;
	private JLabel lbSenha;
	private JLabel lbRepSenha;
	private JPasswordField txSenha;
	private JPasswordField txRepSenha;
	private Boolean ok = Boolean.FALSE;
	private String senha;
	private JPanel panel;
	private JPanel panelBotoes;

	public ValidarSenha() {

		setModal(true);
		setUndecorated(true);
		setSize(320, 156);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Verificação de Senha",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[75px,grow][79px]", "[][][][][grow]"));

		lbSenha = new JLabel("Senha:");
		txRepSenha = new JPasswordField();
		lbRepSenha = new JLabel("Confirmar Senha:");
		txSenha = new JPasswordField();
		panelBotoes = new JPanel();
		btConfirmar = new JButton("Confirmar");
		btCancelar = new JButton("Cancelar");

		btConfirmar.setIcon(ImageUtil.resize("ok.png", 22, 22));
		btCancelar.setIcon(ImageUtil.resize("cancel.png", 22, 22));

		btConfirmar.addActionListener((e) -> validarSenha());
		btCancelar.addActionListener((e) -> cancelar());

		panel.add(lbSenha, "cell 0 0,alignx left,aligny center");
		panel.add(txRepSenha, "cell 0 1 2 1,growx,aligny center");
		panel.add(lbRepSenha, "cell 0 2,alignx left,aligny center");
		panel.add(txSenha, "cell 0 3 2 1,growx,aligny center");
		panel.add(panelBotoes, "cell 0 4 2 1,grow");

		panelBotoes.add(btConfirmar);
		panelBotoes.add(btCancelar);

	}

	private void cancelar() {

		dispose();
	}

	private void validarSenha() {

		String senha = StringUtil.arrayToString(txSenha.getPassword());

		if (senha.length() < 3) {
			JOptionPane.showMessageDialog(null, "A senha deve possuir mais de três caracteres");

			return;
		}

		if (!senha.equals(StringUtil.arrayToString(txRepSenha.getPassword()))) {
			JOptionPane.showMessageDialog(null, "Campos diferentes");

			return;
		}

		ok = Boolean.TRUE;

		this.senha = CryptoUtil.toMD5(senha);

		dispose();
	}

	public Boolean getOk() {
		return ok;
	}

	public String getSenha() {
		return senha;
	}

}

package br.com.sysdesc.components;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import br.com.sysdesc.util.classes.CryptoUtil;
import br.com.sysdesc.util.classes.StringUtil;

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

	public ValidarSenha() {

		setModal(true);
		setUndecorated(true);
		setSize(320, 200);
		setLocationRelativeTo(null);

		lbSenha = new JLabel("Senha:");
		lbRepSenha = new JLabel("Confirmar Senha:");
		txSenha = new JPasswordField();
		txRepSenha = new JPasswordField();
		btConfirmar = new JButton("Confirmar");
		btCancelar = new JButton("Cancelar");

		lbSenha.setBounds(10, 10, 100, 25);
		txSenha.setBounds(10, 35, 300, 25);
		lbRepSenha.setBounds(10, 70, 100, 25);
		txRepSenha.setBounds(10, 95, 300, 25);
		btConfirmar.setBounds(55, 140, 100, 25);
		btCancelar.setBounds(165, 140, 100, 25);

		getContentPane().setLayout(null);

		btConfirmar.addActionListener((e) -> validarSenha());
		btCancelar.addActionListener((e) -> cancelar());
		getContentPane().add(lbSenha);
		getContentPane().add(txSenha);
		getContentPane().add(lbRepSenha);
		getContentPane().add(txRepSenha);
		getContentPane().add(btConfirmar);
		getContentPane().add(btCancelar);
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

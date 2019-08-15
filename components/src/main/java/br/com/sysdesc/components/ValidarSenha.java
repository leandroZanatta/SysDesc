package br.com.sysdesc.components;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class ValidarSenha extends JDialog {

	private JLabel lbSenha;
	private JLabel lbRepSenha;
	private JPasswordField txSenha;
	private JPasswordField txRepSenha;

	public ValidarSenha() {
		setModal(true);
		setUndecorated(true);
		setSize(320, 200);
		setLocationRelativeTo(null);

		lbSenha = new JLabel("Senha:");
		lbRepSenha = new JLabel("Confirmar Senha:");
		txSenha = new JPasswordField();
		txRepSenha = new JPasswordField();

		lbSenha.setBounds(10, 10, 100, 25);
		txSenha.setBounds(10, 35, 300, 25);
		lbRepSenha.setBounds(10, 70, 100, 25);
		txRepSenha.setBounds(10, 95, 300, 25);

		getContentPane().setLayout(null);

		getContentPane().add(lbSenha);
		getContentPane().add(txSenha);
		getContentPane().add(lbRepSenha);
		getContentPane().add(txRepSenha);

	}

	public static void main(String[] args) {
		new ValidarSenha().setVisible(true);
	}

}

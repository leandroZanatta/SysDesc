package br.com.sysdesc.components;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.sysdesc.util.classes.ImageUtil;

public class CampoPesquisa extends JPanel {

	private JButton btnNewButton;

	public CampoPesquisa() {

		setLayout(new BorderLayout(5, 0));

		textField = new JTextField();
		btnNewButton = new JButton();

		btnNewButton.setIcon(ImageUtil.resize("search.png", 15, 15));
		btnNewButton.setMargin(new Insets(0, 0, 0, 0));

		add(textField);
		add(btnNewButton, BorderLayout.EAST);
	}

	private static final long serialVersionUID = 1L;
	private JTextField textField;

}

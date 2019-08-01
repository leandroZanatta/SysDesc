package br.com.sysdesc.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmCliente extends AbstractInternalFrame {
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_1;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;

	public FrmCliente(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		setSize(600, 440);
		setClosable(Boolean.TRUE);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow]",
				"[][][][][][][][][][][][][][][][][grow]"));

		JLabel lblCdigo = new JLabel("Código:");
		getContentPane().add(lblCdigo, "cell 0 0");

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		getContentPane().add(lblCpfcnpj, "cell 5 0 2 1");

		textField = new JTextField();
		getContentPane().add(textField, "cell 0 1,growx");
		textField.setColumns(10);

		JRadioButton rdbtnFisca = new JRadioButton("Pessoa Fisíca");
		getContentPane().add(rdbtnFisca, "flowx,cell 1 1 2 1,alignx right");

		JRadioButton rdbtnJurdica = new JRadioButton("Pessoa Jurídica");
		getContentPane().add(rdbtnJurdica, "cell 3 1 2 1");

		textField_10 = new JTextField();
		getContentPane().add(textField_10, "cell 5 1 2 1,growx");
		textField_10.setColumns(10);

		JLabel lblRazoSocial = new JLabel("Razão Social:");
		getContentPane().add(lblRazoSocial, "cell 0 2");

		textField_2 = new JTextField();
		getContentPane().add(textField_2, "cell 0 3 7 1,growx");
		textField_2.setColumns(10);

		JLabel lblInscrioEstadual = new JLabel("Inscrição Estadual:");
		getContentPane().add(lblInscrioEstadual, "cell 0 4");

		JLabel lblDataNascimento = new JLabel("Data Nascimento:");
		getContentPane().add(lblDataNascimento, "cell 5 4");

		textField_3 = new JTextField();
		getContentPane().add(textField_3, "flowy,cell 0 5 5 1,growx");
		textField_3.setColumns(10);

		textField_8 = new JTextField();
		getContentPane().add(textField_8, "cell 5 5 2 1,growx");
		textField_8.setColumns(10);

		JLabel lblEstado = new JLabel("Estado:");
		getContentPane().add(lblEstado, "cell 0 6");

		JLabel lblCidade = new JLabel("Cidade:");
		getContentPane().add(lblCidade, "cell 4 6");

		JComboBox comboBox = new JComboBox();
		getContentPane().add(comboBox, "cell 0 7 4 1,growx");

		JComboBox comboBox_3 = new JComboBox();
		getContentPane().add(comboBox_3, "cell 4 7 3 1,growx");

		JLabel lblEndereo = new JLabel("Endereço:");
		getContentPane().add(lblEndereo, "cell 0 8");

		JLabel lblNmero = new JLabel("Número:");
		getContentPane().add(lblNmero, "cell 6 8");

		textField_4 = new JTextField();
		getContentPane().add(textField_4, "cell 0 9 6 1,growx");
		textField_4.setColumns(10);

		textField_7 = new JTextField();
		getContentPane().add(textField_7, "cell 6 9,growx");
		textField_7.setColumns(10);

		JLabel lblNewLabel = new JLabel("Bairro:");
		getContentPane().add(lblNewLabel, "cell 0 10");

		JLabel lblCep = new JLabel("Cep:");
		getContentPane().add(lblCep, "cell 5 10");

		textField_5 = new JTextField();
		getContentPane().add(textField_5, "cell 0 11 5 1,growx");
		textField_5.setColumns(10);

		textField_9 = new JTextField();
		getContentPane().add(textField_9, "cell 5 11 2 1,growx");
		textField_9.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Telefone:");
		getContentPane().add(lblNewLabel_1, "cell 0 12");

		JLabel lblEmail = new JLabel("Email:");
		getContentPane().add(lblEmail, "cell 3 12");

		textField_6 = new JTextField();
		getContentPane().add(textField_6, "cell 0 13 3 1,growx");
		textField_6.setColumns(10);

		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 3 13 4 1,growx");
		textField_1.setColumns(10);

		JLabel lblEstadoCivil = new JLabel("Estado Civil:");
		getContentPane().add(lblEstadoCivil, "cell 0 14");

		JLabel lblSexo = new JLabel("Sexo:");
		getContentPane().add(lblSexo, "cell 2 14");

		JLabel lblSituao = new JLabel("Situação:");
		getContentPane().add(lblSituao, "cell 5 14");

		JComboBox comboBox_1 = new JComboBox();
		getContentPane().add(comboBox_1, "cell 0 15 2 1,growx");

		JComboBox comboBox_2 = new JComboBox();
		getContentPane().add(comboBox_2, "cell 2 15 3 1,growx");

		JComboBox comboBox_4 = new JComboBox();
		getContentPane().add(comboBox_4, "cell 5 15 2 1,growx");

	}

}

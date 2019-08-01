package br.com.sysdesc.ui;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class FrmCliente extends AbstractInternalFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	public FrmCliente(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		setSize(606, 409);
		setClosable(Boolean.TRUE);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][][][][grow][][][][][][][]", "[][grow][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));
		
		JLabel lblCdigo = new JLabel("CÓDIGO");
		getContentPane().add(lblCdigo, "cell 0 0");
		
		JLabel lblCpf = new JLabel("CPF");
		getContentPane().add(lblCpf, "cell 2 0");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 0 1,growx");
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 1 1,grow");
		
		JRadioButton rdbtnFisca = new JRadioButton("Fisíca");
		panel.add(rdbtnFisca);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Jurídica");
		panel.add(rdbtnNewRadioButton);
		
		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 2 1,growx");
		textField_1.setColumns(10);
		
		JLabel lblRazoSocial = new JLabel("Razão Social");
		getContentPane().add(lblRazoSocial, "cell 0 2");
		
		textField_2 = new JTextField();
		getContentPane().add(textField_2, "cell 0 3,growx");
		textField_2.setColumns(10);
		
		JLabel lblInscrioEstadual = new JLabel("Inscrição Estadual");
		getContentPane().add(lblInscrioEstadual, "cell 0 4");
		
		textField_3 = new JTextField();
		getContentPane().add(textField_3, "flowy,cell 0 5,growx");
		textField_3.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado");
		getContentPane().add(lblEstado, "cell 0 6");
		
		JComboBox comboBox = new JComboBox();
		getContentPane().add(comboBox, "cell 0 7,growx");
		
		JLabel lblEndereo = new JLabel("Endereço");
		getContentPane().add(lblEndereo, "cell 0 8");
		
		textField_4 = new JTextField();
		getContentPane().add(textField_4, "cell 0 9,growx");
		textField_4.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Bairro");
		getContentPane().add(lblNewLabel, "cell 0 10");
		
		textField_5 = new JTextField();
		getContentPane().add(textField_5, "cell 0 11,growx");
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		getContentPane().add(lblNewLabel_1, "cell 0 12");

	}

}

package br.com.sysdesc.ui;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.cidade.CidadeService;
import br.com.sysdesc.service.cliente.ClienteService;
import br.com.sysdesc.service.estado.EstadoService;
import net.miginfocom.swing.MigLayout;

public class FrmFormasPagamento extends AbstractInternalFrame {
	private ClienteService clienteService = new ClienteService();
	private EstadoService estadoService = new EstadoService();
	private CidadeService cidadeService = new CidadeService();
	private final JLabel label = new JLabel("");
	private JTextField textField;
	private JTextField textField_1;

	public FrmFormasPagamento(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);
		setTitle("Cadastro De Formad DE pagamento");

		setSize(600, 440);
		setClosable(Boolean.TRUE);
		getContentPane().setLayout(new MigLayout("", "[280.00px][grow]", "[1px][][][][][][][][][][]"));
		getContentPane().add(label, "cell 0 0,growx,aligny top");

		JLabel lblCdigo = new JLabel("código:");
		getContentPane().add(lblCdigo, "cell 0 1");

		textField = new JTextField();
		getContentPane().add(textField, "cell 0 2,alignx left");
		textField.setColumns(10);

		JLabel lblDescrio = new JLabel("Descrição:");
		getContentPane().add(lblDescrio, "cell 0 3");

		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 0 4 2 1,growx");
		textField_1.setColumns(10);

		JLabel lblFormasDePagamento = new JLabel(" Formas De pagamento");
		getContentPane().add(lblFormasDePagamento, "cell 0 5");

		JComboBox comboBox = new JComboBox();
		getContentPane().add(comboBox, "cell 0 6 2 1,growx");

		JCheckBox chckbxNewCheckBox = new JCheckBox("Permite Troco");
		getContentPane().add(chckbxNewCheckBox, "flowy,cell 0 8");

		JCheckBox chckbxUsaTef = new JCheckBox("Usa Tef");
		getContentPane().add(chckbxUsaTef, "cell 1 8");

	}
}

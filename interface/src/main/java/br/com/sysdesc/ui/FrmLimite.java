package br.com.sysdesc.ui;

import static javax.swing.border.TitledBorder.CENTER;
import static javax.swing.border.TitledBorder.TOP;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JMoneyField;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.Limite;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.cliente.ClienteService;
import br.com.sysdesc.service.limite.LimiteService;
import br.com.sysdesc.util.resources.Resources;
import net.miginfocom.swing.MigLayout;

public class FrmLimite extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lbCliente;
	private JLabel lbCheque;
	private JLabel lbConvenio;
	private JLabel lbCrediario;
	private JLabel lbValorExtraCheque;
	private JLabel lbVencimentoCheque;
	private JLabel lbValorExtraConvenio;
	private JLabel lbVencimentoConvenio;
	private JLabel lbValorExtraCrediario;
	private JLabel lbVencimentoCrediario;

	private CampoPesquisa<Cliente> pesCliente;

	private JDateChooser txVencimentoCrediario;
	private JDateChooser txVencimentoConvenio;
	private JDateChooser txVencimentoCheque;

	private JMoneyField txLimiteCheque;
	private JMoneyField txLimiteConvenio;
	private JMoneyField txLimiteCrediario;
	private JMoneyField txValorExtraCheque;
	private JMoneyField txValorExtraCrediario;
	private JMoneyField txValorExtraConvenio;

	private JCheckBox chUsaLimiteConvenio;
	private JCheckBox chUsaLimiteCheque;
	private JCheckBox chUsaLimiteCrediario;

	private JPanel panelLimiteExtraCrediario;

	private JPanel panelContent;
	private JPanel panelLimiteExtraConvenio;
	private JPanel pnlLimites;
	private JPanel panelLimiteExtraCheque;

	private PanelActions<Limite> panelActions;

	private ClienteService clientePesquisa = new ClienteService();

	private LimiteService limitePesquisa = new LimiteService();

	public FrmLimite(PermissaoPrograma permissaoPrograma, Long codigoUsuario, Long codigoEmpresa) {
		super(permissaoPrograma, codigoUsuario, codigoEmpresa);

		initComponentes();
	}

	private void initComponentes() {
		setSize(450, 450);
		setClosable(Boolean.TRUE);
		setTitle("Cadastro de Limite");
		Resources.translate(Resources.FRMLIMITE_TITLE);

		EtchedBorder bordaGravada = new EtchedBorder(EtchedBorder.LOWERED, null, null);

		TitledBorder bordaLimites = createTitleBorder(bordaGravada, "Limites");
		TitledBorder bordaCheque = createTitleBorder(bordaGravada, "Limite Extra de Cheque");
		TitledBorder bordaConvenio = createTitleBorder(bordaGravada, "Limite Extra de Convênio");
		TitledBorder bordaCrediario = createTitleBorder(bordaGravada, "Limite Extra de Credário");

		panelContent = new JPanel();
		pnlLimites = new JPanel();
		panelLimiteExtraCheque = new JPanel();
		panelLimiteExtraConvenio = new JPanel();
		panelLimiteExtraCrediario = new JPanel();

		lbCliente = new JLabel("Cliente:");
		lbCheque = new JLabel("Cheque:");
		lbConvenio = new JLabel("Convênio:");
		lbCrediario = new JLabel("Crediário:");
		lbValorExtraCheque = new JLabel("Valor:");
		lbVencimentoCheque = new JLabel("Vencimento:");
		lbValorExtraConvenio = new JLabel("Valor:");
		lbVencimentoConvenio = new JLabel("Vencimento:");
		lbValorExtraCrediario = new JLabel("Valor:");
		lbVencimentoCrediario = new JLabel("Vencimento:");

		chUsaLimiteCrediario = new JCheckBox("Utiliza Limite");
		chUsaLimiteCheque = new JCheckBox("Utiliza Limite");
		chUsaLimiteConvenio = new JCheckBox("Utiliza Limite");

		pesCliente = new CampoPesquisa<Cliente>(clientePesquisa, PesquisaEnum.PES_CLIENTES, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Cliente objeto) {

				return String.format("%d - %s", objeto.getIdCliente(), objeto.getNome());
			}
		};

		txLimiteCheque = new JMoneyField();
		txLimiteConvenio = new JMoneyField();
		txLimiteCrediario = new JMoneyField();
		txValorExtraCheque = new JMoneyField();
		txValorExtraConvenio = new JMoneyField();
		txValorExtraCrediario = new JMoneyField();

		txVencimentoCheque = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		txVencimentoConvenio = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		txVencimentoCrediario = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');

		pnlLimites.setBorder(bordaLimites);
		panelLimiteExtraCheque.setBorder(bordaCheque);
		panelLimiteExtraConvenio.setBorder(bordaConvenio);
		panelLimiteExtraCrediario.setBorder(bordaCrediario);

		panelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		pnlLimites.setLayout(new MigLayout("insets 0 0 0 0", "[grow][grow][grow]", "[][]"));
		panelLimiteExtraCheque.setLayout(new MigLayout("insets 0 0 0 0", "[][grow][grow]", "[grow][]"));
		panelLimiteExtraConvenio.setLayout(new MigLayout("insets 0 0 0 0", "[][grow][grow]", "[][]"));
		panelLimiteExtraCrediario.setLayout(new MigLayout("insets 0 0 0 0", "[][grow][grow]", "[][]"));

		pnlLimites.add(lbCheque, "cell 0 0");
		pnlLimites.add(lbConvenio, "cell 1 0");
		pnlLimites.add(lbCrediario, "cell 2 0");
		pnlLimites.add(txLimiteCheque, "cell 0 1,growx");
		pnlLimites.add(txLimiteConvenio, "cell 1 1,growx");
		pnlLimites.add(txLimiteCrediario, "cell 2 1,growx");

		panelLimiteExtraCheque.add(lbValorExtraCheque, "cell 1 0");
		panelLimiteExtraCheque.add(lbVencimentoCheque, "cell 2 0");
		panelLimiteExtraCheque.add(chUsaLimiteCheque, "cell 0 1");
		panelLimiteExtraCheque.add(txValorExtraCheque, "cell 1 1,growx");
		panelLimiteExtraCheque.add(txVencimentoCheque, "cell 2 1,growx");

		panelLimiteExtraConvenio.add(lbValorExtraConvenio, "cell 1 0");
		panelLimiteExtraConvenio.add(lbVencimentoConvenio, "cell 2 0");
		panelLimiteExtraConvenio.add(chUsaLimiteConvenio, "cell 0 1");
		panelLimiteExtraConvenio.add(txValorExtraConvenio, "cell 1 1,growx");
		panelLimiteExtraConvenio.add(txVencimentoConvenio, "cell 2 1,growx");

		panelLimiteExtraCrediario.add(lbValorExtraCrediario, "cell 1 0");
		panelLimiteExtraCrediario.add(lbVencimentoCrediario, "cell 2 0");
		panelLimiteExtraCrediario.add(chUsaLimiteCrediario, "cell 0 1");
		panelLimiteExtraCrediario.add(txValorExtraCrediario, "cell 1 1,growx");
		panelLimiteExtraCrediario.add(txVencimentoCrediario, "cell 2 1,growx");

		panelContent.add(panelLimiteExtraCheque, "cell 0 3,grow");
		panelContent.add(panelLimiteExtraConvenio, "cell 0 4,grow");
		panelContent.add(panelLimiteExtraCrediario, "cell 0 5,grow");
		panelContent.add(lbCliente, "cell 0 0");
		panelContent.add(pesCliente, "cell 0 1,growx");
		panelContent.add(pnlLimites, "cell 0 2,grow");

		getContentPane().add(panelContent);

		panelActions = new PanelActions<Limite>(this, limitePesquisa, PesquisaEnum.PES_LIMITES) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Limite objeto) {

			}

			@Override
			public Boolean preencherObjeto(Limite objetoPesquisa) {

				return Boolean.TRUE;
			}

		};
		panelContent.add(panelActions, "cell 0 6,grow");

	}

	private TitledBorder createTitleBorder(EtchedBorder bordaGravada, String title) {

		return new TitledBorder(bordaGravada, title, CENTER, TOP, null, null);
	}

}

package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_PDVS;
import static br.com.sysdesc.util.resources.Resources.FRMPDV_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMPDV_LB_IPPDV;
import static br.com.sysdesc.util.resources.Resources.FRMPDV_LB_NUMEROPDV;
import static br.com.sysdesc.util.resources.Resources.FRMPDV_LB_SITUACAO;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.enumerator.TipoStatusEnum;
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.Pdv;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.pdv.PDVService;
import net.miginfocom.swing.MigLayout;

public class FrmPDVS extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	private JLabel lblCodigo;
	private JLabel lblNumeroDoPdv;
	private JLabel lblIpDoPdv;
	private JLabel lblSitucao;

	private JTextFieldId txCodigo;
	private JNumericField txNumeroPDV;
	private JTextField txIPPDV;
	private JComboBox<TipoStatusEnum> cbSituacao;

	private PanelActions<Pdv> panelActions;

	private PDVService pdvService = new PDVService();

	public FrmPDVS(PermissaoPrograma permissaoPrograma, Long codigoUsuario, Long codigoEmpresa) {
		super(permissaoPrograma, codigoUsuario, codigoEmpresa);

		initComponentes();
	}

	private void initComponentes() {
		setSize(500, 170);
		setClosable(Boolean.TRUE);
		setTitle("Cadastro de PDVS");

		painelContent = new JPanel();

		lblCodigo = new JLabel(translate(FRMPDV_LB_CODIGO));
		lblNumeroDoPdv = new JLabel(translate(FRMPDV_LB_NUMEROPDV));
		lblIpDoPdv = new JLabel(translate(FRMPDV_LB_IPPDV));
		lblSitucao = new JLabel(translate(FRMPDV_LB_SITUACAO));

		txIPPDV = new JTextField();
		txCodigo = new JTextFieldId();
		txNumeroPDV = new JNumericField();
		cbSituacao = new JComboBox<>(TipoStatusEnum.values());

		painelContent.setLayout(new MigLayout("", "[][grow][grow]", "[][][][][grow]"));

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(lblNumeroDoPdv, "cell 0 2");
		painelContent.add(lblIpDoPdv, "cell 1 2");
		painelContent.add(lblSitucao, "cell 2 2");

		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(txNumeroPDV, "cell 0 3,width 50:120:120");
		painelContent.add(txIPPDV, "cell 1 3,growx");
		painelContent.add(cbSituacao, "cell 2 3,growx");

		getContentPane().add(painelContent);

		panelActions = new PanelActions<Pdv>(this, pdvService, PES_PDVS, Boolean.TRUE) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Pdv objeto) {

				txCodigo.setValue(objeto.getIdPdv());
				txNumeroPDV.setValue(objeto.getNumeroPDV());
				txIPPDV.setText(objeto.getIpPDV());
				cbSituacao.setSelectedItem(TipoStatusEnum.findByCodigo(objeto.getSituacao()));
			}

			@Override
			public Boolean preencherObjeto(Pdv objetoPesquisa) {

				objetoPesquisa.setIdPdv(txCodigo.getValue());
				objetoPesquisa.setNumeroPDV(txNumeroPDV.getValue());
				objetoPesquisa.setIpPDV(txIPPDV.getText());

				TipoStatusEnum tipoStatusEnum = (TipoStatusEnum) cbSituacao.getSelectedItem();

				Long situacao = null;

				if (tipoStatusEnum != null) {
					situacao = tipoStatusEnum.getCodigo();
				}

				objetoPesquisa.setSituacao(situacao);

				return Boolean.TRUE;
			}

		};

		panelActions.addSaveListener((pdv) -> txCodigo.setValue(pdv.getIdPdv()));

		painelContent.add(panelActions, "cell 0 4 3 1,grow");

	}

}

package br.com.sysdesc.pesquisa.components;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.ui.FrmPesquisa;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.ImageUtil;
import net.miginfocom.swing.MigLayout;

public abstract class CampoPesquisa<T> extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btPesquisa;
	private JTextField txValorPesquisa;
	private final AbstractGenericService<T> genericService;
	private PesquisaEnum pesquisaEnum;
	private Long codigoUsuario;
	private T objetoPesquisado;
	private Boolean pesquisaOk = Boolean.FALSE;

	public CampoPesquisa(AbstractGenericService<T> genericService, PesquisaEnum pesquisaEnum, Long codigoUsuario) {

		this.genericService = genericService;
		this.pesquisaEnum = pesquisaEnum;
		this.codigoUsuario = codigoUsuario;

		initComponents();
	}

	private void initComponents() {
		txValorPesquisa = new JTextField();
		btPesquisa = new JButton();

		btPesquisa.setIcon(ImageUtil.resize("search.png", 16, 16));
		btPesquisa.setMargin(new Insets(0, 0, 0, 0));
		btPesquisa.addActionListener((e) -> validarPesquisa());

		txValorPesquisa.setEditable(Boolean.FALSE);

		setLayout(new MigLayout("", "[grow][0.00]", "[grow]"));

		add(txValorPesquisa, "growx");
		add(btPesquisa, "east");
	}

	private void validarPesquisa() {

		if (validar()) {
			abrirPesquisa();
		}
	}

	public Boolean validar() {
		return Boolean.TRUE;
	}

	private void abrirPesquisa() {

		JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);

		FrmPesquisa<T> frmPesquisa = new FrmPesquisa<T>(parent, pesquisaEnum, getPreFilter(), genericService,
				codigoUsuario);

		frmPesquisa.setVisible(Boolean.TRUE);

		this.pesquisaOk = frmPesquisa.getOk();

		if (frmPesquisa.getOk()) {

			this.objetoPesquisado = frmPesquisa.getObjeto();

			this.carregarCampo(txValorPesquisa, this.objetoPesquisado);

			return;
		}

		txValorPesquisa.setText("");
		this.objetoPesquisado = null;

	}

	public abstract void carregarCampo(JTextField campoPesquisa, T objeto);

	public Boolean getPesquisaOk() {
		return pesquisaOk;
	}

	public T getObjetoPesquisado() {
		return objetoPesquisado;
	}

	public BooleanBuilder getPreFilter() {

		return new BooleanBuilder();
	}

}

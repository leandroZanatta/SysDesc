package br.com.sysdesc.pesquisa.components;

import static br.com.sysdesc.util.resources.Resources.OPTION_VALIDACAO;
import static br.com.sysdesc.util.resources.Resources.translate;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.components.listeners.ChangeListener;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.ui.FrmPesquisa;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.exception.SysDescException;
import net.miginfocom.swing.MigLayout;

public abstract class CampoPesquisa<T> extends JPanel {

	private static final long serialVersionUID = 1L;

	private final AbstractGenericService<T> genericService;
	private final PesquisaEnum pesquisaEnum;
	private final Long codigoUsuario;

	private JButton btPesquisa;
	private JTextField txValorPesquisa;
	private T objetoPesquisado;
	private Boolean pesquisaOk = Boolean.FALSE;
	private BooleanBuilder preFilter;
	protected EventListenerList listener = new EventListenerList();

	public CampoPesquisa(AbstractGenericService<T> genericService, PesquisaEnum pesquisaEnum, Long codigoUsuario) {

		this(genericService, pesquisaEnum, codigoUsuario, new BooleanBuilder());
	}

	public CampoPesquisa(AbstractGenericService<T> genericService, PesquisaEnum pesquisaEnum, Long codigoUsuario,
			BooleanBuilder preFilter) {

		this.genericService = genericService;
		this.pesquisaEnum = pesquisaEnum;
		this.codigoUsuario = codigoUsuario;
		this.preFilter = preFilter;

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

		try {

			JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);

			FrmPesquisa<T> frmPesquisa = new FrmPesquisa<T>(parent, pesquisaEnum, getPreFilter(), genericService,
					codigoUsuario);

			frmPesquisa.setVisible(Boolean.TRUE);

			this.pesquisaOk = frmPesquisa.getOk();

			if (frmPesquisa.getOk()) {

				fireChangeEvent(frmPesquisa.getObjeto(), this.objetoPesquisado);

				this.objetoPesquisado = frmPesquisa.getObjeto();

				carregarCampo();

				return;
			}

		} catch (SysDescException e) {
			showMessageDialog(null, e.getMensagem(), translate(OPTION_VALIDACAO), JOptionPane.WARNING_MESSAGE);
		}

		txValorPesquisa.setText("");
		this.objetoPesquisado = null;

	}

	private void carregarCampo() {

		if (this.objetoPesquisado == null) {

			txValorPesquisa.setText("");

			return;
		}

		txValorPesquisa.setText(this.formatarValorCampo(this.objetoPesquisado));
	}

	public void setValue(T objeto) {

		this.objetoPesquisado = objeto;

		carregarCampo();
	}

	@SuppressWarnings("unchecked")
	public void fireChangeEvent(T newValue, T oldValue) {
		Object[] listeners = listener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == ChangeListener.class) {

				((ChangeListener<T>) listeners[i + 1]).valueChanged(newValue, oldValue);
			}
		}
	}

	public void limpar() {

		this.objetoPesquisado = null;

		this.txValorPesquisa.setText("");
	}

	public void addChangeListener(ChangeListener<T> changeListener) {

		listener.add(ChangeListener.class, changeListener);
	}

	public abstract String formatarValorCampo(T objeto);

	public Boolean getPesquisaOk() {
		return pesquisaOk;
	}

	public T getObjetoPesquisado() {
		return objetoPesquisado;
	}

	public BooleanBuilder getPreFilter() {

		return this.preFilter;
	}

	public void bloquear(Boolean editable) {
		btPesquisa.setEnabled(editable);
	}

	public void setValueById(Long id) {
		
		setValue(genericService.buscarPorId(id));
	}

}

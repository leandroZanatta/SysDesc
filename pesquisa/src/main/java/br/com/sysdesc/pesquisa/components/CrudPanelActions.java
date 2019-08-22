package br.com.sysdesc.pesquisa.components;

import static br.com.sysdesc.util.resources.Resources.OPTION_VALIDACAO;
import static br.com.sysdesc.util.resources.Resources.translate;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.listeners.ButtonActionListener;
import br.com.sysdesc.components.listeners.PanelActionListener;
import br.com.sysdesc.pesquisa.components.buttonactions.AbstractButtonAction;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonAction;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionBuscar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionCancelar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionEditar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionNovo;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionSalvar;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.ui.FrmPesquisa;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.ClassTypeUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.exception.SysDescException;

public abstract class CrudPanelActions<T> extends AbstractButtonAction {

	private static final long serialVersionUID = 1L;

	private AbstractGenericService<T> genericService;
	private PesquisaEnum pesquisa;
	private ButtonActionSalvar btSalvar;
	private ButtonActionEditar btEditar;
	private ButtonActionNovo btNovo;
	private ButtonActionBuscar btBuscar;
	private ButtonActionCancelar btCancelar;

	private final JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
	protected T objetoPesquisa;
	protected List<ButtonAction> actions = new ArrayList<>();
	private ActionMap actionMap = new ActionMap();
	private InputMap inputMap;
	protected EventListenerList buttonListener = new EventListenerList();

	public CrudPanelActions(AbstractInternalFrame internalFrame, AbstractGenericService<T> genericService,
			PesquisaEnum pesquisa, ButtonAction... actions) {
		super(internalFrame);

		this.pesquisa = pesquisa;
		this.genericService = genericService;

		initComponents(actions);
	}

	private void initComponents(ButtonAction... actions) {

		inputMap = internalFrame.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);

		btSalvar = new ButtonActionSalvar();
		btEditar = new ButtonActionEditar();
		btNovo = new ButtonActionNovo();
		btBuscar = new ButtonActionBuscar();
		btCancelar = new ButtonActionCancelar();

		final CrudPanelActions<T> painel = this;

		Action actionSalvar = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				saveEvent();
			}
		};

		Action actionEditar = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				editEvent();
			}
		};

		Action actionNovo = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				newEvent(painel);
			}
		};

		Action actionLocalizar = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				searchEvent();

			}
		};

		Action actionCancelar = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				internalFrame.dispose();

			}
		};

		registrarEvento(btSalvar, actionSalvar);
		registrarEvento(btEditar, actionEditar);
		registrarEvento(btNovo, actionNovo);
		registrarEvento(btBuscar, actionLocalizar);
		registrarEvento(btCancelar, actionCancelar);

		posicionarEAdicionarBotoes(Arrays.asList(actions));

		internalFrame.getRootPane().setActionMap(actionMap);

		registrarCampos();

		limpar();

		bloquear(Boolean.TRUE);

		fireButtonListenerStarted();
	}

	protected void registrarEvento(ButtonAction buttonAction, Action action) {

		buttonAction.addActionListener(action);

		this.addButtonListener(buttonAction);

		if (!StringUtil.isNullOrEmpty(buttonAction.getMapName())) {

			inputMap.put(buttonAction.getKeyStroke(), buttonAction.getMapName());

			actionMap.put(buttonAction.getMapName(), action);

		}
	}

	private void searchEvent() {

		pesquisar();

		if (objetoPesquisa != null) {

			limpar();

			carregarObjeto(objetoPesquisa);

			fireButtonListenerSearched();
		}
	}

	private void editEvent() {

		bloquear(Boolean.FALSE);

		fireButtonListenerEdited();
	}

	private void posicionarEAdicionarBotoes(List<ButtonAction> actions) {

		this.actions.addAll(actions);
		this.actions.add(btSalvar);
		this.actions.add(btEditar);
		this.actions.add(btNovo);
		this.actions.add(btBuscar);
		this.actions.add(btCancelar);

		posicionarBotoes();

		this.actions.stream().sorted(Comparator.comparing(ButtonAction::getOrdem)).forEach(this::add);
	}

	private void saveEvent() {

		try {

			preencherObjeto(objetoPesquisa);

			genericService.validar(objetoPesquisa);

			genericService.salvar(objetoPesquisa);

			bloquear(Boolean.TRUE);

			fireSaveEvent(objetoPesquisa);

		} catch (SysDescException sysDescException) {

			showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
		}

	}

	protected void posicionarBotoes() {
		btSalvar.setOrdem(0);
		btEditar.setOrdem(1);
		btNovo.setOrdem(2);
		btBuscar.setOrdem(3);
		btCancelar.setOrdem(4);
	}

	public void pesquisar() {
		try {

			FrmPesquisa<T> pesquisa = new FrmPesquisa<>(parent, this.pesquisa, getPreFilter(), this.genericService,
					this.internalFrame.getCodigoUsuario());

			pesquisa.setVisible(Boolean.TRUE);

			if (pesquisa.getOk()) {

				this.objetoPesquisa = pesquisa.getObjeto();

				limpar();

				bloquear(Boolean.TRUE);

				carregarObjeto(objetoPesquisa);
			}
		} catch (SysDescException e) {

			showMessageDialog(parent, e.getMensagem(), translate(OPTION_VALIDACAO), JOptionPane.WARNING_MESSAGE);
		}
	}

	protected abstract void carregarObjeto(T objetoPesquisa);

	public BooleanBuilder getPreFilter() {

		return new BooleanBuilder();
	}

	@Override
	protected void fireClearEvent() {

	}

	@Override
	protected void fireChangeStateEvent(Boolean bloquear) {

	}

	public abstract void preencherObjeto(T objetoPesquisa);

	public void addButtonListener(ButtonActionListener buttonActionListener) {

		buttonListener.add(ButtonActionListener.class, buttonActionListener);
	}

	public void removeButtonListener(ButtonActionListener buttonActionListener) {

		buttonListener.remove(ButtonActionListener.class, buttonActionListener);
	}

	@SuppressWarnings("unchecked")
	public void fireSaveEvent(T evt) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == PanelActionListener.class) {

				((PanelActionListener<T>) listeners[i + 1]).saveEvent(evt);
			}
		}

		fireButtonListenerSaved();
	}

	@SuppressWarnings("unchecked")
	private void newEvent(CrudPanelActions<T> painel) {

		try {

			objetoPesquisa = (T) ClassTypeUtil.getGenericType(painel.getClass()).newInstance();

			limpar();

			bloquear(Boolean.FALSE);

			fireNewEvent();

			fireButtonListenerNew();

		} catch (IllegalAccessException | InstantiationException e1) {
			e1.printStackTrace();
		}

	}

	public void fireNewEvent() {

		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == PanelActionListener.class) {

				((PanelActionListener<?>) listeners[i + 1]).newEvent();
			}
		}
	}

	private void fireButtonListenerNew() {

		Object[] listeners = buttonListener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == ButtonActionListener.class) {

				((ButtonActionListener) listeners[i + 1]).newEvent();
			}
		}

	}

	private void fireButtonListenerSearched() {

		Object[] listeners = buttonListener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == ButtonActionListener.class) {

				((ButtonActionListener) listeners[i + 1]).searchEvent();
			}
		}
	}

	private void fireButtonListenerSaved() {

		Object[] listeners = buttonListener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == ButtonActionListener.class) {

				((ButtonActionListener) listeners[i + 1]).saveEvent();
			}
		}
	}

	private void fireButtonListenerEdited() {

		Object[] listeners = buttonListener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == ButtonActionListener.class) {

				((ButtonActionListener) listeners[i + 1]).editEvent();
			}
		}

	}

	private void fireButtonListenerStarted() {
		Object[] listeners = buttonListener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == ButtonActionListener.class) {

				((ButtonActionListener) listeners[i + 1]).startEvent();
			}
		}
	}

}

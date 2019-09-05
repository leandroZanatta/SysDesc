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
import java.util.function.Consumer;

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
import br.com.sysdesc.components.listeners.NewListener;
import br.com.sysdesc.components.listeners.SaveListener;
import br.com.sysdesc.pesquisa.components.buttonactions.AbstractButtonAction;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonAction;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionAvancar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionBuscar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionCancelar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionEditar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionNovo;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionPrimeiro;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionRetroceder;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionSalvar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionUltimo;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.ui.FrmPesquisa;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.ClassTypeUtil;
import br.com.sysdesc.util.classes.ContadorUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.exception.SysDescException;

public abstract class PanelActions<T> extends AbstractButtonAction {

    private static final long serialVersionUID = 1L;

    private AbstractGenericService<T> genericService;
    private PesquisaEnum pesquisa;
    protected ButtonActionSalvar btSalvar;
    protected ButtonActionEditar btEditar;
    protected ButtonActionNovo btNovo;
    protected ButtonActionBuscar btBuscar;
    protected ButtonActionCancelar btCancelar;

    protected ButtonActionAvancar btAvancar;
    protected ButtonActionRetroceder btRetroceder;
    protected ButtonActionPrimeiro btPrimeiro;
    protected ButtonActionUltimo btUltimo;

    private final JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
    private final Boolean pageable;
    protected T objetoPesquisa;
    protected List<ButtonAction> actions = new ArrayList<>();
    private ActionMap actionMap = new ActionMap();
    private InputMap inputMap;
    protected EventListenerList buttonListener = new EventListenerList();
    protected EventListenerList saveListener = new EventListenerList();
    protected EventListenerList newListener = new EventListenerList();

    public PanelActions(AbstractInternalFrame internalFrame, AbstractGenericService<T> genericService, PesquisaEnum pesquisa,
            ButtonAction... actions) {

        this(internalFrame, genericService, pesquisa, Boolean.TRUE, actions);
    }

    public PanelActions(AbstractInternalFrame internalFrame, AbstractGenericService<T> genericService, PesquisaEnum pesquisa, Boolean pageable,
            ButtonAction... actions) {

        super(internalFrame);

        this.pageable = pageable;
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

        final PanelActions<T> painel = this;

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

        if (pageable) {

            btAvancar = new ButtonActionAvancar();
            btRetroceder = new ButtonActionRetroceder();
            btUltimo = new ButtonActionUltimo();
            btPrimeiro = new ButtonActionPrimeiro();

            Action actionAvancar = new AbstractAction() {

                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    avancarEvent();
                }
            };

            Action actionRetroceder = new AbstractAction() {

                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    retrocederEvent();
                }
            };

            Action actionUltimo = new AbstractAction() {

                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    ultimoEvent();
                }
            };

            Action actionPrimeiro = new AbstractAction() {

                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    primeiroEvent();
                }
            };

            registrarEvento(btAvancar, actionAvancar);
            registrarEvento(btRetroceder, actionRetroceder);
            registrarEvento(btUltimo, actionUltimo);
            registrarEvento(btPrimeiro, actionPrimeiro);

        }
        registrarEventosBotoesPagina();

        registrarEvento(btSalvar, actionSalvar);
        registrarEvento(btEditar, actionEditar);
        registrarEvento(btNovo, actionNovo);
        registrarEvento(btBuscar, actionLocalizar);
        registrarEvento(btCancelar, actionCancelar);

        posicionarEAdicionarBotoes(Arrays.asList(actions));

        internalFrame.getRootPane().setActionMap(actionMap);

        registrarCampos();

        limpar();

        setEditable(Boolean.FALSE);
        bloquear();

        fireButtonListener(ButtonActionListener::startEvent);
    }

    protected void registrarEventosBotoesPagina() {
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

            fireButtonListener(ButtonActionListener::searchEvent);
        }
    }

    private void editEvent() {

        setEditable(Boolean.TRUE);

        bloquear();

        fireButtonListener(ButtonActionListener::editEvent);
    }

    private void posicionarEAdicionarBotoes(List<ButtonAction> actions) {

        this.actions.addAll(actions);
        this.actions.add(btSalvar);
        this.actions.add(btEditar);
        this.actions.add(btNovo);
        this.actions.add(btBuscar);
        this.actions.add(btCancelar);

        if (pageable) {

            this.actions.add(btAvancar);
            this.actions.add(btRetroceder);
            this.actions.add(btUltimo);
            this.actions.add(btPrimeiro);
        }

        posicionarBotoes();

        this.actions.stream().sorted(Comparator.comparing(ButtonAction::getOrdem)).forEach(this::add);
    }

    private void saveEvent() {

        try {

            if (preencherObjeto(objetoPesquisa)) {

                genericService.validar(objetoPesquisa);

                genericService.salvar(objetoPesquisa);

                setEditable(Boolean.FALSE);

                bloquear();

                fireSaveEvent(objetoPesquisa);

                fireButtonListener(ButtonActionListener::saveEvent);
            }

        } catch (SysDescException sysDescException) {

            showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
        }

    }

    protected void posicionarBotoes() {

        ContadorUtil contadorUtil = new ContadorUtil();

        posicionarBotao(contadorUtil, btPrimeiro, pageable);
        posicionarBotao(contadorUtil, btRetroceder, pageable);

        posicionarBotao(contadorUtil, btSalvar, Boolean.TRUE);
        posicionarBotao(contadorUtil, btEditar, Boolean.TRUE);
        posicionarBotao(contadorUtil, btNovo, Boolean.TRUE);
        posicionarBotao(contadorUtil, btBuscar, Boolean.TRUE);
        posicionarBotao(contadorUtil, btCancelar, Boolean.TRUE);

        posicionarBotao(contadorUtil, btAvancar, pageable);
        posicionarBotao(contadorUtil, btUltimo, pageable);

    }

    protected void posicionarBotao(ContadorUtil contadorUtil, ButtonAction buttonAction, Boolean adicionar) {

        if (adicionar) {
            buttonAction.setOrdem(contadorUtil.next().intValue());
        }
    }

    private void retrocederEvent() {

        try {

            objetoPesquisa = genericService.previows(getValueId());

            limpar();

            setEditable(Boolean.FALSE);

            bloquear();

            carregarObjeto(objetoPesquisa);

            fireButtonListener(ButtonActionListener::searchEvent);

        } catch (SysDescException sysDescException) {

            showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
        }

    }

    private Long getValueId() {

        Long valor = null;

        if (objetoPesquisa != null) {
            valor = genericService.getId().apply(objetoPesquisa);
        }

        return valor;
    }

    private void ultimoEvent() {

        try {
            objetoPesquisa = genericService.last();

            limpar();

            setEditable(Boolean.FALSE);

            bloquear();

            carregarObjeto(objetoPesquisa);

            fireButtonListener(ButtonActionListener::searchEvent);

        } catch (SysDescException sysDescException) {

            showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
        }
    }

    private void primeiroEvent() {

        try {

            objetoPesquisa = genericService.first();

            limpar();

            setEditable(Boolean.FALSE);

            bloquear();

            carregarObjeto(objetoPesquisa);

            fireButtonListener(ButtonActionListener::searchEvent);

        } catch (SysDescException sysDescException) {

            showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
        }

    }

    private void avancarEvent() {

        try {

            objetoPesquisa = genericService.next(getValueId());

            limpar();

            setEditable(Boolean.FALSE);

            bloquear();

            carregarObjeto(objetoPesquisa);

            fireButtonListener(ButtonActionListener::searchEvent);

        } catch (SysDescException sysDescException) {

            showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
        }

    }

    public void pesquisar() {
        try {

            FrmPesquisa<T> pesquisa = new FrmPesquisa<>(parent, this.pesquisa, getPreFilter(), this.genericService,
                    this.internalFrame.getCodigoUsuario());

            pesquisa.setVisible(Boolean.TRUE);

            if (pesquisa.getOk()) {

                this.objetoPesquisa = pesquisa.getObjeto();

                limpar();

                setEditable(Boolean.FALSE);

                bloquear();

                carregarObjeto(objetoPesquisa);
            }
        } catch (SysDescException e) {

            showMessageDialog(parent, e.getMensagem(), translate(OPTION_VALIDACAO), JOptionPane.WARNING_MESSAGE);
        }
    }

    public BooleanBuilder getPreFilter() {

        return new BooleanBuilder();
    }

    public void addButtonListener(ButtonActionListener buttonActionListener) {

        buttonListener.add(ButtonActionListener.class, buttonActionListener);
    }

    public void addSaveListener(SaveListener<T> saveListener) {

        this.saveListener.add(SaveListener.class, saveListener);
    }

    public void addNewListener(NewListener newListener) {

        this.newListener.add(NewListener.class, newListener);
    }

    @SuppressWarnings("unchecked")
    private void newEvent(PanelActions<T> painel) {

        try {

            objetoPesquisa = (T) ClassTypeUtil.getGenericType(painel.getClass()).newInstance();

            limpar();

            setEditable(Boolean.TRUE);

            bloquear();

            fireButtonListener(ButtonActionListener::newEvent);

        } catch (IllegalAccessException | InstantiationException e1) {
            e1.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public void fireSaveEvent(T evt) {
        Object[] listeners = saveListener.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2) {

            if (listeners[i] == SaveListener.class) {

                ((SaveListener<T>) listeners[i + 1]).saveEvent(evt);
            }
        }
    }

    private void fireButtonListener(Consumer<ButtonActionListener> consumer) {

        Object[] listeners = buttonListener.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2) {

            if (listeners[i] == ButtonActionListener.class) {

                consumer.accept(((ButtonActionListener) listeners[i + 1]));
            }
        }
    }

    @Override
    protected void fireClearEvent() {

        Object[] listeners = newListener.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2) {

            if (listeners[i] == SaveListener.class) {

                ((NewListener) listeners[i + 1]).newEvent();
            }
        }
    }

    public T getObjetoPesquisa() {
        return objetoPesquisa;
    }

    protected abstract void carregarObjeto(T objetoPesquisa);

    public abstract Boolean preencherObjeto(T objetoPesquisa);

}

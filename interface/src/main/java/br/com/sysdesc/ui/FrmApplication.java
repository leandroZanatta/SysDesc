package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMAPPLICATION_LB_USUARIO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.enumerator.ProgramasEnum;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Programa;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.main.MainService;
import br.com.sysdesc.thread.AtualizacaoThread;
import br.com.sysdesc.thread.TimerThread;
import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.classes.ListUtil;
import br.com.sysdesc.util.classes.StringUtil;
import net.miginfocom.swing.MigLayout;

public class FrmApplication extends JFrame {

	private static final String SYS_DESC = "SysDesc";

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static Usuario usuario;
	private static JLabel lbUsuario;
	private JMenuBar menuBar;
	private JDesktopPane desktopPane;
	private JToolBar toolBar;
	private JPanel panel;
	private JPanel panel_3;
	private JLabel lbHorario;
	private static FrmApplication frmApplication;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_4;

	public FrmApplication() {

		initComponents();

	}

	private void initComponents() {

		contentPane = new JPanel();
		menuBar = new JMenuBar();
		desktopPane = new JDesktopPane();
		toolBar = new JToolBar();
		panel = new JPanel();
		panel_3 = new JPanel();

		contentPane.setLayout(new BorderLayout(0, 0));
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		contentPane.add(panel, BorderLayout.SOUTH);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		contentPane.add(toolBar, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[grow][400px][]", "[]"));

		panel_1 = new JPanel();
		panel.add(panel_1, "cell 0 0,alignx left,aligny center");
		lbUsuario = new JLabel();
		panel_1.add(lbUsuario);

		panel_2 = new JPanel();
		panel.add(panel_2, "cell 1 0,alignx center");

		AtualizacaoThread atualizacaoThread = new AtualizacaoThread(panel_2);

		panel_4 = new JPanel();
		panel.add(panel_4, "cell 2 0,alignx right");
		lbHorario = new JLabel();
		panel_4.add(lbHorario);

		TimerThread timerThread = new TimerThread(lbHorario);

		timerThread.start();
		atualizacaoThread.start();

		setJMenuBar(menuBar);
		setTitle(SYS_DESC);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 600);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setContentPane(contentPane);

	}

	private void getSingleInstance(Class<? extends AbstractInternalFrame> frame, PermissaoPrograma permissaoPrograma) {

		try {

			Constructor<? extends AbstractInternalFrame> constructor = frame.getConstructor(PermissaoPrograma.class,
					Long.class);

			AbstractInternalFrame internalFrame = constructor.newInstance(permissaoPrograma, usuario.getIdUsuario());

			desktopPane.add(internalFrame);

			Dimension desktopSize = desktopPane.getSize();
			Dimension jInternalFrameSize = internalFrame.getSize();

			internalFrame.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
					(desktopSize.height - jInternalFrameSize.height) / 2);

			internalFrame.setVisible(Boolean.TRUE);

		} catch (Exception e) {

			e.printStackTrace();

			JOptionPane.showMessageDialog(this, "ERRO");
		}

	}

	public static FrmApplication getInstance() {

		if (frmApplication == null) {

			frmApplication = new FrmApplication();

			frmApplication.setVisible(Boolean.TRUE);

			FrmLogin frmLogin = new FrmLogin(frmApplication);

			frmLogin.setVisible(Boolean.TRUE);

			usuario = frmLogin.getUsuario();

			frmApplication.createMenus();

			String formattedUser = String.format(" %s - %s", usuario.getIdUsuario(), usuario.getCliente().getNome());

			lbUsuario.setText(translate(FRMAPPLICATION_LB_USUARIO) + formattedUser);
		}

		return frmApplication;
	}

	private void createMenus() {

		MainService mainService = new MainService();

		List<Programa> permissoes = mainService.buscarPermissaoUsuario(usuario.getIdUsuario());

		permissoes.forEach(menu -> {

			if (!ListUtil.isNullOrEmpty(menu.getProgramas())) {

				JMenu menuToolbar = new JMenu(translate(menu.getDescricao()));

				menuBar.add(menuToolbar);

				menu.getProgramas().forEach(programa -> {

					createSubMenus(menuToolbar, programa);

				});
			}

		});

		menuBar.repaint();
		toolBar.repaint();
	}

	private void createSubMenus(JMenu menuToolbar, Programa menu) {

		if (!ListUtil.isNullOrEmpty(menu.getProgramas())) {

			menu.getProgramas().forEach(programa -> {

				JMenu submenu = new JMenu(translate(programa.getDescricao()));

				if (!StringUtil.isNullOrEmpty(programa.getIcone())) {
					submenu.setIcon(ImageUtil.resize(programa.getIcone(), 15, 15));
				}

				menuToolbar.add(submenu);

				createSubMenus(submenu, programa);
			});

			return;
		}

		JMenuItem menuitem = new JMenuItem(translate(menu.getDescricao()));

		ProgramasEnum programa = ProgramasEnum.findByCodigo(menu.getIdPrograma());

		if (programa != null) {
			menuitem.addActionListener(
					(e) -> getSingleInstance(programa.getInternalFrame(), menu.getPermissaoProgramas().get(0)));
		}

		if (!StringUtil.isNullOrEmpty(menu.getIcone())) {

			menuitem.setIcon(ImageUtil.resize(menu.getIcone(), 15, 15));

			if (menu.getFlagAcessoRapido()) {

				JButton botao = new JButton(ImageUtil.resize(menu.getIcone(), 15, 15));

				botao.setToolTipText(translate(menu.getDescricao()));

				botao.addActionListener(
						(e) -> getSingleInstance(programa.getInternalFrame(), menu.getPermissaoProgramas().get(0)));

				toolBar.add(botao);
			}
		}

		menuToolbar.add(menuitem);

	}
}

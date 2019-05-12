package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMAPPLICATION_LB_USUARIO;
import static br.com.sysdesc.util.resources.Resources.FRMAPPLICATION_MI_USUARIOS;
import static br.com.sysdesc.util.resources.Resources.FRMAPPLICATION_MN_CADASTRO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.thread.TimerThread;
import br.com.sysdesc.util.classes.ImageUtil;

public class FrmApplication extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static Usuario usuario;
	private static JLabel lbUsuario;
	private JMenuBar menuBar;
	private JMenu mnCadastro;
	private JMenuItem mniiUsuarios;
	private JDesktopPane desktopPane;
	private JToolBar toolBar;
	private JButton btnNewButton;
	private JPanel panel;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lbHorario;
	private static FrmApplication frmApplication;
	private List<AbstractInternalFrame> frames = new ArrayList<>();

	public FrmApplication() {
		contentPane = new JPanel();
		menuBar = new JMenuBar();
		mnCadastro = new JMenu(translate(FRMAPPLICATION_MN_CADASTRO));
		mniiUsuarios = new JMenuItem(translate(FRMAPPLICATION_MI_USUARIOS));
		desktopPane = new JDesktopPane();
		toolBar = new JToolBar();
		btnNewButton = new JButton();
		panel = new JPanel();
		panel_2 = new JPanel();
		panel_3 = new JPanel();
		lbHorario = new JLabel();
		lbUsuario = new JLabel();

		TimerThread timerThread = new TimerThread(lbHorario);
		GridBagLayout gbl_panel = new GridBagLayout();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();

		menuBar.add(mnCadastro);
		mnCadastro.add(mniiUsuarios);

		mniiUsuarios.setIcon(ImageUtil.resize("user.png", 16, 16));
		btnNewButton.setIcon(ImageUtil.resize("user.png", 23, 23));

		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 10, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };

		flowLayout.setAlignment(FlowLayout.LEFT);

		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 0;

		contentPane.setLayout(new BorderLayout(0, 0));
		panel.setLayout(gbl_panel);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		btnNewButton.addActionListener((e) -> getSingleInstance(FrmUsuarios.class));
		mniiUsuarios.addActionListener((e) -> getSingleInstance(FrmUsuarios.class));

		contentPane.add(panel, BorderLayout.SOUTH);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		contentPane.add(toolBar, BorderLayout.NORTH);
		toolBar.add(btnNewButton);
		panel.add(panel_2, gbc_panel_2);
		panel.add(panel_3, gbc_panel_3);
		panel_3.add(lbHorario);
		panel_2.add(lbUsuario);

		timerThread.start();

		setJMenuBar(menuBar);
		setTitle("SYSDESC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 600);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setContentPane(contentPane);

	}

	private void getSingleInstance(Class<? extends AbstractInternalFrame> frame) {

		try {

			Optional<AbstractInternalFrame> optional = frames.stream().filter(x -> x.getClass().equals(frame))
					.findFirst();

			if (!optional.isPresent()) {

				Constructor<? extends AbstractInternalFrame> constructor = frame.getConstructor(FrmApplication.class);

				optional = Optional.of(constructor.newInstance(this));

				desktopPane.add(optional.get());

				Dimension desktopSize = desktopPane.getSize();
				Dimension jInternalFrameSize = optional.get().getSize();

				optional.get().setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
						(desktopSize.height - jInternalFrameSize.height) / 2);

				this.frames.add(optional.get());
			}

			optional.get().setVisible(Boolean.TRUE);

		} catch (Exception e) {
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

			String formattedUser = String.format(" %s - %s", usuario.getIdUsuario(), usuario.getCliente().getNome());

			lbUsuario.setText(translate(FRMAPPLICATION_LB_USUARIO) + formattedUser);
		}

		return frmApplication;
	}

	public void closeFrame(AbstractInternalFrame abstractInternalFrame) {

		this.frames.remove(abstractInternalFrame);
	}

}

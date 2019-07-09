package br.com.sysdesc.pesquisa.components;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

public abstract class CampoPesquisaMultiSelect<T> extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btPesquisa;
	private JTextField txValorPesquisa;
	private AbstractGenericService<T> genericService;
	private PesquisaEnum pesquisaEnum;
	private Long codigoUsuario;
	private List<T> objetosPesquisados;
	private Boolean pesquisaOk = Boolean.FALSE;

	public CampoPesquisaMultiSelect(AbstractGenericService<T> genericService, PesquisaEnum pesquisaEnum,
			Long codigoUsuario) {

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
		btPesquisa.addActionListener((e) -> abrirPesquisa());

		txValorPesquisa.setEditable(Boolean.FALSE);

		setLayout(new MigLayout("", "[grow][0.00]", "[grow]"));

		add(txValorPesquisa, "growx");
		add(btPesquisa, "east");
	}

	private void abrirPesquisa() {

		JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);

		FrmPesquisa<T> frmPesquisa = new FrmPesquisa<T>(parent, pesquisaEnum, getPreFilter(), genericService,
				codigoUsuario, Boolean.TRUE);

		frmPesquisa.setVisible(Boolean.TRUE);

		this.pesquisaOk = frmPesquisa.getOk();

		if (frmPesquisa.getOk()) {

			this.objetosPesquisados = frmPesquisa.getObjetos();

			this.carregarCampo();

			return;
		}

		txValorPesquisa.setText("");
		this.objetosPesquisados = new ArrayList<>();

	}

	protected <K> void carregarCampo() {

		if (this.objetosPesquisados.size() == 1) {

			T objeto = this.objetosPesquisados.get(0);

			txValorPesquisa.setText(String.format("%d - %s", getId().apply(objeto), getDescricao().apply(objeto)));

			return;
		}

		txValorPesquisa.setText(objetosPesquisados.stream().map(x -> getId().apply(x).toString())
				.collect(Collectors.joining(",", "< ", " >")));

	};

	public abstract Function<T, Long> getId();

	public abstract Function<T, String> getDescricao();

	public Boolean getPesquisaOk() {
		return pesquisaOk;
	}

	public List<T> getObjetosPesquisado() {
		return objetosPesquisados;
	}

	public BooleanBuilder getPreFilter() {

		return new BooleanBuilder();
	}
}

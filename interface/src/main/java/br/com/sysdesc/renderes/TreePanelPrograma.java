package br.com.sysdesc.renderes;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import br.com.sysdesc.repository.model.Programa;
import br.com.sysdesc.service.programa.ProgramaService;
import br.com.sysdesc.util.classes.ListUtil;

public class TreePanelPrograma extends JTree {

	private static final long serialVersionUID = 1L;

	private ProgramaService programaService = new ProgramaService();

	private Long niveis = 0L;

	public TreePanelPrograma() {
		this(new CheckNodePrograma());
	}

	public TreePanelPrograma(CheckNodePrograma node) {

		programaService.buscarRootProgramas().forEach(programa -> node.addNode(this.createNode(programa, node)));

		this.niveis = node.getNivel();

		setModel(new DefaultTreeModel(node, false));
	}

	private CheckNodePrograma createNode(Programa programa, CheckNodePrograma nodes) {

		CheckNodePrograma checkNode = new CheckNodePrograma(programa, !ListUtil.isNullOrEmpty(programa.getProgramas()),
				Boolean.FALSE);

		if (!ListUtil.isNullOrEmpty(programa.getProgramas())) {

			programa.getProgramas().forEach(item -> checkNode.addNode(createNode(item, checkNode)));

		}

		return checkNode;
	}

	public Long getNiveis() {
		return niveis;
	}

	public void setNiveis(Long niveis) {
		this.niveis = niveis;
	}

}

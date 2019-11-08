package br.com.sysdesc.util.integracao.cases;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.swing.JTextField;

import br.com.sysdesc.enumerator.ProgramasEnum;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.ui.FrmApplication;
import br.com.sysdesc.ui.FrmDepartamento;
import br.com.sysdesc.util.FrmUtil;

public class DepartamentoCases {

	public static void testCadastroDepartamento(FrmApplication frmApplication,
			List<PermissaoPrograma> permissaoProgramas) throws Exception {

		FrmDepartamento frmDepartamento = (FrmDepartamento) FrmUtil.openInstance(frmApplication, permissaoProgramas,
				ProgramasEnum.CADASTRO_DEPARTAMENTOS);

		JTextField txDescricao = FrmUtil.getFied(frmDepartamento, FrmDepartamento.class, "txDescricao");
		JTextField txCodigo = FrmUtil.getFied(frmDepartamento, FrmDepartamento.class, "txCodigo");

		assertTrue(!txDescricao.isEditable());
		assertTrue(!txCodigo.isEditable());

		FrmUtil.novoRegistro(frmDepartamento, FrmDepartamento.class);

		assertTrue(txDescricao.isEditable());
		assertTrue(!txCodigo.isEditable());

		txDescricao.setText("cerveja");

		FrmUtil.salvarRegistro(frmDepartamento, FrmDepartamento.class);

		Thread.sleep(10000);

	}

}

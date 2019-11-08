package br.com.sysdesc.util.integracao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.naming.ConfigurationException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.sysdesc.atualizacao.enumeradores.TipoConexaoEnum;
import br.com.sysdesc.atualizacao.util.resources.Configuracoes;
import br.com.sysdesc.repository.conexao.Conexao;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.ui.FrmApplication;
import br.com.sysdesc.ui.FrmLogin;
import br.com.sysdesc.ui.util.LookAndFeelUtil;
import br.com.sysdesc.util.FrmUtil;
import br.com.sysdesc.util.integracao.cases.DepartamentoCases;
import br.com.sysdesc.util.integracao.cases.LoginCases;

@RunWith(MockitoJUnitRunner.class)
public class IntegracaoGeral {

	@Test
	public void testarIntegracao() throws Exception {
		try {
			Class<?>[] parameters = {};

			Object[] valores = {};

			deletarArquivoDb();

			LookAndFeelUtil.configureLayout();

			new GeracaoBaseVazia().gerarBaseVazia();

			FrmApplication frmApplication = new FrmApplication();
			frmApplication.setVisible(Boolean.TRUE);

			FrmLogin frmLogin = new FrmLogin(frmApplication);

			Usuario usuario = LoginCases.autenticacao(frmLogin);

			FrmUtil.setarUsuario(frmApplication, usuario);

			FrmUtil.executeVoidMethod(frmApplication, "createMenus", parameters, valores);
			FrmUtil.executeVoidMethod(frmApplication, "setarLabels", parameters, valores);

			DepartamentoCases.testCadastroDepartamento(frmApplication, usuario.getPermissaoProgramas());
		} catch (Exception e) {

			e.printStackTrace();

			fail(e.getMessage());
		}
	}

	private void deletarArquivoDb() throws ConfigurationException, IOException {

		String configuracao = Configuracoes.USER_DIR + Configuracoes.SEPARATOR + "config" + Configuracoes.SEPARATOR
				+ "config.02";

		Properties propriedadeconf = Conexao.buscarPropertiesConexao(new File(configuracao));

		String fileh2 = propriedadeconf.getProperty(TipoConexaoEnum.jdbcUrl).split(":")[3];

		File arquivoDb = new File(
				Configuracoes.USER_DIR + Configuracoes.SEPARATOR + fileh2.substring(2, fileh2.length()) + ".mv.db");

		File arquivoTrace = new File(
				Configuracoes.USER_DIR + Configuracoes.SEPARATOR + fileh2.substring(2, fileh2.length()) + ".trace.db");

		if (arquivoDb.exists()) {

			FileUtils.forceDelete(arquivoDb);
		}

		if (arquivoTrace.exists()) {

			FileUtils.forceDelete(arquivoTrace);
		}
	}
}

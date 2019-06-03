package br.com.sysdesc.util.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Resources {

	private Resources() {
	}

	private static Properties mapaValores;
	private static Charset charset = Charset.forName("UTF-8");

	public static final String FRMLOGIN_LB_LOGIN = "FRMLOGIN_LB_LOGIN";
	public static final String FRMLOGIN_BT_LOGAR = "FRMLOGIN_BT_LOGAR";
	public static final String FRMLOGIN_LB_USUARIO = "FRMLOGIN_LB_USUARIO";
	public static final String FRMLOGIN_LB_SENHA = "FRMLOGIN_LB_SENHA";
	public static final String FRMLOGIN_BT_CANCELAR = "FRMLOGIN_BT_CANCELAR";
	public static final String FRMLOGIN_MSG_SENHA = "FRMLOGIN_MSG_SENHA";
	public static final String FRMLOGIN_MSG_USUARIO = "FRMLOGIN_MSG_USUARIO";
	public static final String FRMLOGIN_MSG_LOGIN = "FRMLOGIN_MSG_LOGIN";
	public static final String FRMLOGIN_MSG_VERIFICACAO = "FRMLOGIN_MSG_VERIFICACAO";

	public static final String TBLCONFIG_DESCRICAO = "TBLCONFIG_DESCRICAO";
	public static final String TBLCONFIG_ALIAS = "TBLCONFIG_ALIAS";
	public static final String TBLCONFIG_TIPO_DADO = "TBLCONFIG_TIPO_DADO";
	public static final String TBLCONFIG_CAMPO = "TBLCONFIG_CAMPO";
	public static final String TBLCONFIG_FORMATO = "TBLCONFIG_FORMATO";
	public static final String TBLCONFIG_TIPO_TAMANHO = "TBLCONFIG_TIPO_TAMANHO";
	public static final String TBLCONFIG_TAMANHO = "TBLCONFIG_TAMANHO";

	public static final String FRMCONEXAO_TITULO = "FRMCONEXAO_TITULO";
	public static final String FRMCONEXAO_MSG_SALVAR = "FRMCONEXAO_MSG_SALVAR";
	public static final String FRMCONEXAO_PRP_CONEXAO = "FRMCONEXAO_PRP_CONEXAO";
	public static final String FRMCONEXAO_BT_SALVAR = "FRMCONEXAO_BT_SALVAR";
	public static final String FRMCONEXAO_BT_CANCELAR = "FRMCONEXAO_BT_CANCELAR";
	public static final String FRMCONEXAO_LB_TIPOBANCO = "FRMCONEXAO_LB_TIPOBANCO";
	public static final String FRMCONEXAO_LB_URL = "FRMCONEXAO_LB_URL";
	public static final String FRMCONEXAO_LB_PORTA = "FRMCONEXAO_LB_PORTA";
	public static final String FRMCONEXAO_LB_USUARIO = "FRMCONEXAO_LB_USUARIO";
	public static final String FRMCONEXAO_LB_SENHA = "FRMCONEXAO_LB_SENHA";
	public static final String FRMCONEXAO_LB_BANCO = "FRMCONEXAO_LB_BANCO";

	public static final String FRMUSUARIO_TITLE = "FRMUSUARIO_TITLE";
	public static final String FRMUSUARIO_LB_CODIGO = "FRMUSUARIO_LB_CODIGO";
	public static final String FRMUSUARIO_LB_USUARIO = "FRMUSUARIO_LB_USUARIO";
	public static final String FRMUSUARIO_LB_SENHA = "FRMUSUARIO_LB_SENHA";

	public static final String FRMAPPLICATION_MN_CADASTRO = "FRMAPPLICATION_MN_CADASTRO";
	public static final String FRMAPPLICATION_MI_USUARIOS = "FRMAPPLICATION_MI_USUARIOS";
	public static final String FRMAPPLICATION_LB_USUARIO = "FRMAPPLICATION_LB_USUARIO";

	public static final String DRIVER_NAO_ENCONTRADO = "DRIVER_NAO_ENCONTRADO";
	public static final String CONEXAO_INVALIDA = "CONEXAO_INVALIDA";

	public static final String FRMDEPARTAMENTO_TITLE = "FRMDEPARTAMENTO_TITLE";
	public static final String FRMDEPARTAMENTO_LB_CODIGO = "FRMDEPARTAMENTO_LB_CODIGO";
	public static final String FRMDEPARTAMENTO_LB_DESCRICAO = "FRMDEPARTAMENTO_LB_DESCRICAO";
	static {
		try {
			File arquivoConfiguracao = new File(Configuracoes.RESOURCES);

			if (!arquivoConfiguracao.exists()) {
				FileUtils.writeStringToFile(arquivoConfiguracao, "pt_br.properties", charset);
			}

			String arquivoConfig = FileUtils.readFileToString(arquivoConfiguracao, charset);

			mapaValores = new Properties();

			mapaValores.load(new InputStreamReader(
					new FileInputStream(Configuracoes.PATH_RESOURCES + "\\" + arquivoConfig), charset));

		} catch (IOException e) {

		}
	}

	public static String translate(String menu) {

		return mapaValores.getProperty(menu);

	}

}

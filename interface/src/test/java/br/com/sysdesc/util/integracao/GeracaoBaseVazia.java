package br.com.sysdesc.util.integracao;

import static java.sql.DriverManager.getConnection;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.util.Properties;

import br.com.sysdesc.atualizacao.changelog.core.Changelog;
import br.com.sysdesc.atualizacao.enumeradores.TipoConexaoEnum;
import br.com.sysdesc.atualizacao.util.resources.Configuracoes;
import br.com.sysdesc.repository.conexao.Conexao;

public class GeracaoBaseVazia {

    public void gerarBaseVazia() throws Exception {

        try {
            File path = new File(Configuracoes.USER_DIR).getParentFile().getParentFile();

            File pastaChangesets = new File(path, "\\sysdesc-upgrade\\upgrade");

            String configuracao = Configuracoes.USER_DIR + Configuracoes.SEPARATOR + "config" + Configuracoes.SEPARATOR + "config.02";

            Changelog.runChangelog(buscarConexao(configuracao), pastaChangesets.getAbsolutePath(), Configuracoes.CHANGELOG);

            Conexao.createConnection(new File(configuracao));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        assertTrue(true);
    }

    public static Connection buscarConexao(String configuracao) throws Exception {

        Properties propertiesConexao = Conexao.buscarPropertiesConexao(new File(configuracao));

        String clazz = propertiesConexao.getProperty(TipoConexaoEnum.jdbcDriver);
        String url = propertiesConexao.getProperty(TipoConexaoEnum.jdbcUrl);
        String usuario = propertiesConexao.getProperty(TipoConexaoEnum.jdbcUser);
        String senha = propertiesConexao.getProperty(TipoConexaoEnum.jdbcPassword);

        Class.forName(clazz);

        return getConnection(url, usuario, senha);

    }

}

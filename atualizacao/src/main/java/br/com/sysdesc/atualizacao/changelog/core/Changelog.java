package br.com.sysdesc.atualizacao.changelog.core;

import java.sql.Connection;
import java.util.logging.Logger;

import br.com.sysdesc.atualizacao.util.resources.Configuracoes;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

public class Changelog {

	public static void runChangelog(Connection connection) {

		Logger log = Logger.getLogger(Changelog.class.getName());

		Database database;

		try {

			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

			Liquibase liquibase = new liquibase.Liquibase(Configuracoes.CHANGELOG,
					new FileSystemResourceAccessor(Configuracoes.UPGRADE), database);

			liquibase.update(new Contexts(), new LabelExpression());

		} catch (LiquibaseException e) {

			log.severe("OCORREU UM ERRO AO ATUALIZAR A BASE DE DADOS");

			e.printStackTrace();

			System.exit(0);
		}

	}
}

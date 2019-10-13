package br.com.sysdesc.upgrade.changelog.core;

import java.sql.Connection;

import br.com.sysdesc.upgrade.util.resources.Configuracoes;
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

		Database database;

		try {

			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

			Liquibase liquibase = new liquibase.Liquibase(Configuracoes.CHANGELOG,
					new FileSystemResourceAccessor(Configuracoes.UPGRADE), database);

			liquibase.update(new Contexts(), new LabelExpression());

		} catch (LiquibaseException e) {

			System.exit(0);
		}

	}

}

package br.com.sysdesc.repository.core;

import java.sql.Connection;

import javax.persistence.EntityManager;

import br.com.sysdesc.repository.conexao.Conexao;
import br.com.sysdesc.util.resources.Configuracoes;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class Changelog {

	public static void runChangelog() {

		EntityManager em = Conexao.getEntityManager();

		em.getTransaction().begin();

		java.sql.Connection connection = em.unwrap(Connection.class);

		Database database;

		try {
			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

			Liquibase liquibase = new liquibase.Liquibase(Configuracoes.CHANGELOG, new ClassLoaderResourceAccessor(),
					database);

			liquibase.update(new Contexts(), new LabelExpression());

			em.getTransaction().commit();

		} catch (LiquibaseException e) {

			em.getTransaction().rollback();

			System.exit(0);
		}

	}
}

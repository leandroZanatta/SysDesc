package br.com.sysdesc.upgrade.util.resources;

import java.io.File;

public class Configuracoes {

	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String SEPARATOR = File.separator;

	public static final String UPGRADE = USER_DIR + SEPARATOR + "upgrade";
	public static final String CHANGELOG = "db.changelog-master.xml";
	public static final String CONEXAO = USER_DIR + SEPARATOR + "config" + SEPARATOR + "config.01";

}

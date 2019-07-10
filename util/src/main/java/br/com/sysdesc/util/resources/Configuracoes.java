package br.com.sysdesc.util.resources;

import java.io.File;

public class Configuracoes {

	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String SEPARATOR = File.separator;
	public static final String FOLDER_RESOURCES = USER_DIR + SEPARATOR + "resources";
	public static final String FOLDER_IMAGE = FOLDER_RESOURCES + SEPARATOR + "image";
	public static final String FOLDER_PESQUISAS = FOLDER_RESOURCES + SEPARATOR + "pesquisas";
	public static final String FOLDER_REPORTS = FOLDER_RESOURCES + SEPARATOR + "reports";

	public static final String CONEXAO = USER_DIR + SEPARATOR + "config" + SEPARATOR + "config.01";
	public static final String RESOURCES = USER_DIR + SEPARATOR + "config" + SEPARATOR + "resources.01";
	public static final String PATH_RESOURCES = USER_DIR + SEPARATOR + "resources";
	public static final String UPGRADE = USER_DIR + SEPARATOR + "upgrade";
	public static final String CHANGELOG = UPGRADE + SEPARATOR + "db.changelog-master.xml";

}

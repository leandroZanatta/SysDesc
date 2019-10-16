package br.com.sysdesc.atualizacao.util.resources;

import java.io.File;

public class Configuracoes {

	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String SEPARATOR = File.separator;
	public static final String FOLDER_IMAGE = File.separator + "image";

	public static final String CONEXAO = USER_DIR + SEPARATOR + "config" + SEPARATOR + "config.01";
	public static final String RESOURCES = USER_DIR + SEPARATOR + "config" + SEPARATOR + "resources.01";
	public static final String UPGRADE = USER_DIR + SEPARATOR + "upgrade";
	public static final String CHANGELOG = "db.changelog-master.xml";
	public static final String PATH_VERSOES = USER_DIR + SEPARATOR + "versoes";
	public static final String VERSAO = PATH_VERSOES + SEPARATOR + "versao.json";

}

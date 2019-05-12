package br.com.sysdesc.repository.enumeradores;

public enum TipoConexaoEnum {

	POSTGRES("org.postgresql.Driver", "jdbc:postgresql://localhost",
			"SELECT datname FROM pg_database WHERE datistemplate = false", "postgres", 5432),

	MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "show databases", "", 3306);

	private String driver;

	private String url;

	private String database;

	private String defaultDatabase;

	private Integer porta;

	private String jdbcPassword = "javax.persistence.jdbc.password";
	private String jdbcUser = "javax.persistence.jdbc.user";
	private String jdbcUrl = "javax.persistence.jdbc.url";
	private String jdbcDriver = "javax.persistence.jdbc.driver";

	private TipoConexaoEnum(String driver, String url, String database, String defaultDatabase, Integer porta) {
		this.driver = driver;
		this.url = url;
		this.porta = porta;
		this.database = database;
		this.defaultDatabase = defaultDatabase;

	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public Integer getPorta() {
		return porta;
	}

	public String getDatabase() {
		return database;
	}

	public String getDefaultDatabase() {
		return defaultDatabase;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	public String getJdbcUser() {
		return jdbcUser;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	@Override
	public String toString() {
		return this.driver;
	}
}

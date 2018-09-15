package com.jpm1590.model;

public class DbInserts {
	
	private String dbname;
	private String dbinserts;

	public DbInserts(String dbname, String dbinserts) {
		super();
		this.dbname = dbname;
		this.dbinserts = dbinserts;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDbinserts() {
		return dbinserts;
	}

	public void setDbinserts(String dbinserts) {
		this.dbinserts = dbinserts;
	}
}

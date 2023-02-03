package com.Dextrus.entity;

public class RequestBodyPattern {
	private ConnectionPropertiesFile properties;

	public ConnectionPropertiesFile getProperties() {
		return properties;
	}

	public void setProperties(ConnectionPropertiesFile properties) {
		this.properties = properties;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	private String pattern;
	private String catalog;

}

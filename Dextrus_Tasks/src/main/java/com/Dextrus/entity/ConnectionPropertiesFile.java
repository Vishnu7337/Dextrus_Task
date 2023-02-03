package com.Dextrus.entity;

public class ConnectionPropertiesFile {

	private String url;
	private String userName;
	private String password;
	private boolean testConnection;
	private boolean catalogs;
	private String catalogName;
	private boolean schema;
	private String schemaName;
	
	public boolean isTestConnection() {
		return testConnection;
	}
	public void setTestConnection(boolean testConnection) {
		this.testConnection = testConnection;
	}
	
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isCatalogs() {
		return catalogs;
	}
	public void setCatalogs(boolean catalogs) {
		this.catalogs = catalogs;
	}
	public boolean isSchema() {
		return schema;
	}
	public void setSchema(boolean schema) {
		this.schema = schema;
	}
	
		
}

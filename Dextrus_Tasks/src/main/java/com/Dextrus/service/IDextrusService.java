package com.Dextrus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.Dextrus.entity.ConnectionPropertiesFile;
import com.Dextrus.entity.TableDescription;
import com.Dextrus.entity.TableType;
import com.Dextrus.entity.TablesAndViews;

public interface IDextrusService {
	
	ResponseEntity<String> proessRequest(ConnectionPropertiesFile connectionProperties);

	ResponseEntity<String> testConnection(ConnectionPropertiesFile connectionProperties);

	List<String> getCatalogsList(ConnectionPropertiesFile properties);

	List<String> getSchemasList(ConnectionPropertiesFile properties, String catalog);

	List<TablesAndViews> getTablesAndViews(String catalog, String schema, ConnectionPropertiesFile properties);

	List<TableDescription> getTableDescription(ConnectionPropertiesFile properties, String catalog, String schema,
			String table);

	List<TableType> getTablesAndViewsByPattern(ConnectionPropertiesFile properties, String catalog, String pattern);

	List<List<Object>> getTableData(ConnectionPropertiesFile prop, String query);

}

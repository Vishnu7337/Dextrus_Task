package com.Dextrus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Dextrus.dao.DextrusDaoInterface;

import com.Dextrus.entity.ConnectionPropertiesFile;
import com.Dextrus.entity.TableDescription;
import com.Dextrus.entity.TableType;
import com.Dextrus.entity.TablesAndViews;

@Service
public class DextrusServiceImpl implements IDextrusService {

	@Autowired
	private DextrusDaoInterface dextrusDao;
	
	@Override
	public ResponseEntity<String> testConnection(ConnectionPropertiesFile connectionProperties) {
		
		
	 	ResponseEntity<String>  res=dextrusDao.testConnection(connectionProperties);
	 	return res;
		
	}

	@Override
	public List<String> getCatalogsList(ConnectionPropertiesFile connectionProperties) {
	
		List<String> catalogs=dextrusDao.getCatalogs(connectionProperties);
		return catalogs;
	}

	@Override
	public List<String> getSchemasList(ConnectionPropertiesFile connectionProperties, String catalog) {
		List<String> schemas=dextrusDao.getSchema(connectionProperties,catalog);
		return schemas;
	}

	@Override
	public List<TablesAndViews> getTablesAndViews(String catalog, String schema, ConnectionPropertiesFile properties) {
	List<TablesAndViews> tablesAndViews=dextrusDao.getTablesAndViews(catalog,schema,properties);
		return tablesAndViews;
	}

	@Override
	public List<TableDescription> getTableDescription(ConnectionPropertiesFile properties, String catalog,
			String schema, String table) {
		List<TableDescription> getDescription=dextrusDao.getTableDescription(properties,catalog,schema,table);
		return getDescription;
	}

	

	@Override
	public ResponseEntity<String> proessRequest(ConnectionPropertiesFile connectionProperties) {
		// TODO Auto-generated method stub
		if(connectionProperties.isCatalogs()) {
			
		}
		
		return null;
	}

	@Override
	public List<List<Object>> getTableData(ConnectionPropertiesFile prop, String query) {
		
		List<List<Object>> rows = dextrusDao.getTableData(prop,query);
		
		
		return rows;
	}

	@Override
	public List<TableType> getTablesAndViewsByPattern(ConnectionPropertiesFile properties, String catalog,
			String pattern) {
		List<TableType> tablesAndViews=dextrusDao.getTablesAndViewsByPattern(properties,catalog,pattern);
		return tablesAndViews;
		
	}

	
	

	
}

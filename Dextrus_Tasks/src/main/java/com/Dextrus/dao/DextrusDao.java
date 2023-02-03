package com.Dextrus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.Dextrus.cc.CC;
import com.Dextrus.entity.ConnectionPropertiesFile;
import com.Dextrus.entity.TableDescription;
import com.Dextrus.entity.TableType;
import com.Dextrus.entity.TablesAndViews;

@Repository
public class DextrusDao implements DextrusDaoInterface {

	@Override
	public ResponseEntity<String> testConnection(ConnectionPropertiesFile connectionProperties) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			Connection con = DriverManager.getConnection(connectionProperties.getUrl(),
					connectionProperties.getUserName(), connectionProperties.getPassword());
			return new ResponseEntity<String>("{\"status\":\"Success\"}", HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Not Connected", HttpStatus.NOT_FOUND);

	}

	@Override
	public List<String> getCatalogs(ConnectionPropertiesFile connectionProperties) {

		List<String> catalogs = null;
		Connection connection = CC.getConnection(connectionProperties);
		try {
			ResultSet rs = connection.createStatement().executeQuery("SELECT name FROM sys.databases");
			catalogs = new ArrayList<>();
			while (rs.next()) {
				catalogs.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return catalogs;

	}

	@Override
	public List<String> getSchema(ConnectionPropertiesFile connectionProperties, String catalog) {
		List<String> schemas = null;
		try {
			Connection connection = CC.getConnection(connectionProperties);
			String query = "SELECT name FROM " + catalog + ".sys.schemas";
			ResultSet rs = connection.createStatement().executeQuery(query);
			schemas = new ArrayList<>();
			while (rs.next())
				schemas.add(rs.getString("name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schemas;
	}

	@Override
	public List<TablesAndViews> getTablesAndViews(String catalog, String schema, ConnectionPropertiesFile properties) {
		List<TablesAndViews> viewsAndTables = new ArrayList<>();
		try {
			Connection connection = CC.getConnection(properties);
			PreparedStatement statement = connection.prepareStatement("use " + catalog + "; " + CC.GET_TABLES_QUERY);
			statement.setString(1, catalog);
			statement.setString(2, schema);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				TablesAndViews tablesAndViews = new TablesAndViews();
				tablesAndViews.setTableName(resultSet.getString("TABLE_NAME"));
				tablesAndViews.setTableType(resultSet.getString("TABLE_TYPE"));
				viewsAndTables.add(tablesAndViews);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return viewsAndTables;
	}

	@Override
	public List<TableDescription> getTableDescription(ConnectionPropertiesFile properties, String catalog,
			String schema, String table) {
		List<TableDescription> tableDescList = new ArrayList<>();
		try {
			Connection connection = CC.getConnection(properties);
			PreparedStatement statement = connection.prepareStatement("use " + catalog + "; " + CC.DESCRIPTION_QUERY);
			table = schema + "." + table;
			statement.setString(1, table);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				TableDescription td = new TableDescription();
				td.setColumnName(resultSet.getString("COLUMN_NAME"));
				td.setDataType(resultSet.getString("DATA_TYPE"));
				td.setIsNullable(resultSet.getInt("IS_NULLABLE"));
				td.setPrimaryKey(resultSet.getInt("PRIMARY_KEY"));
				tableDescList.add(td);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableDescList;

	}

	@Override
	public List<TableType> getTablesAndViewsByPattern(ConnectionPropertiesFile properties, String catalog,String pattern) {
		

		List<TableType> viewsAndTables = new ArrayList<>();
		
		try {
			Connection connection = CC.getConnection(properties);
			PreparedStatement statement = connection
					.prepareStatement("use " + catalog + "; " + CC.GET_TABLES_BY_PATTERN_QUERY);
			statement.setString(1, pattern);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				TableType tableType = new TableType();
				tableType.setTableName(resultSet.getString("TABLE_NAME"));
				tableType.setTableType(resultSet.getString("TABLE_TYPE"));
				viewsAndTables.add(tableType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewsAndTables;
			
		}

	@Override
	public List<List<Object>> getTableData(ConnectionPropertiesFile properties, String query) {
		List<List<Object>> rows = new ArrayList<>();
		try {
			Connection con = CC.getConnection(properties);
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			ResultSetMetaData meta = resultSet.getMetaData();
			int columnCount = meta.getColumnCount();
			while (resultSet.next()) {

				List<Object> row = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {

					String columnName = meta.getColumnName(i);
					String columnType = meta.getColumnTypeName(i);
					switch (columnType) {
					case "varchar": {
						row.add(columnName + " : " + resultSet.getString(columnName));
						break;
					}
					case "float": {
						row.add(columnName + " : " + resultSet.getFloat(columnName));
						break;
					}
					case "boolean": {
						row.add(columnName + " : " + resultSet.getBoolean(columnName));
						break;
					}
					case "int": {
						row.add(columnName + " : " + resultSet.getInt(columnName));
						break;
					}
					case "timestamp": {
						row.add(columnName + " : " + resultSet.getTimestamp(columnName));
						break;
					}
					case "decimal": {
						row.add(columnName + " : " + resultSet.getBigDecimal(columnName));
						break;
					}
					case "date": {
						row.add(columnName + " : " + resultSet.getDate(columnName));
						break;
					}
					default:
						row.add("!-!-! " + columnName + " : " + resultSet.getObject(columnName));
						System.out.println("Datatype Not available for Column: " + columnName);
					}
				}
				rows.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

}

package com.Dextrus.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Dextrus.entity.ConnectionPropertiesFile;
import com.Dextrus.entity.RequestBodyPattern;
import com.Dextrus.entity.RequestBodyQuery;
import com.Dextrus.entity.TableDescription;
import com.Dextrus.entity.TableType;
import com.Dextrus.entity.TablesAndViews;
import com.Dextrus.service.IDextrusService;








@RestController
public class DextrusController {
	
	@Autowired
	private IDextrusService dextrusService;
	
	@PostMapping("/getConnection")
	public ResponseEntity<String> testConnection(@RequestBody ConnectionPropertiesFile connectionProperties)
	{
		
		
		ResponseEntity<String> res=dextrusService.testConnection(connectionProperties);
		return res;
		
	}
	
	@GetMapping("/getCatalogs")
	public  ResponseEntity<List<String>> getCatalogs(@RequestBody ConnectionPropertiesFile properties) {
	List<String> catalogs = dextrusService.getCatalogsList(properties);
	
		 
		return new ResponseEntity<List<String>>(catalogs, HttpStatus.OK);
	}
	
	@GetMapping("/{catalog}")
	public ResponseEntity<List<String>> getSchemas(@PathVariable String catalog, @RequestBody ConnectionPropertiesFile properties){
		List<String> schemas = dextrusService.getSchemasList(properties,catalog);
		return new ResponseEntity<List<String>>(schemas, HttpStatus.OK);
	}
	
	
	@GetMapping("/{catalog}/{schema}")
	public ResponseEntity<List<TablesAndViews>> getViewsAndTables( @PathVariable String catalog, @PathVariable String schema, @RequestBody ConnectionPropertiesFile properties){
		List<TablesAndViews> viewsAndTables = dextrusService.getTablesAndViews(catalog,schema,properties);
//		System.out.println(viewsAndTables.size()+"-------");
 		return new ResponseEntity<List<TablesAndViews>>(viewsAndTables, HttpStatus.OK);
	}
	
	@GetMapping("/{catalog}/{schema}/{table}")
	public ResponseEntity<List<TableDescription>> getColumnProperties(@PathVariable String catalog, @PathVariable String schema, @PathVariable String table, @RequestBody ConnectionPropertiesFile properties){
		List<TableDescription> tableDescList = dextrusService.getTableDescription(properties, catalog, schema, table);
		return new ResponseEntity<List<TableDescription>>(tableDescList, HttpStatus.OK);	
	}
	
	@GetMapping("/query")
	public ResponseEntity<List<List<Object>>> testQuery(@RequestBody RequestBodyQuery queryBody ) {
		ConnectionPropertiesFile prop = queryBody.getProperties();
		String query = queryBody.getQuery();
		List<List<Object>> tableDataList = dextrusService.getTableData(prop, query);
		return new ResponseEntity<List<List<Object>>>(tableDataList, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TableType>> getTablesAndViewsByPattern(@RequestBody RequestBodyPattern bodyPattern) {
		
		String pattern="P%";
		List<TableType> viewsAndTables=	dextrusService.getTablesAndViewsByPattern(bodyPattern.getProperties(),bodyPattern.getCatalog(),bodyPattern.getPattern());
		return new ResponseEntity<List<TableType>>(viewsAndTables, HttpStatus.OK);
	}
	

}

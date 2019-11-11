package com.ocheretyany.alexander.REST.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ocheretyany.alexander.REST.exception.NotFoundException;
import com.ocheretyany.alexander.REST.model.Record;
import com.ocheretyany.alexander.REST.repository.ConnectionRepository;

@RestController
public class DBController {

	@Autowired
	ConnectionRepository connectionRepository;
	private Connection connection;
	
    /**
     * Lists all schemas
     * @param recordId - the ID of a record that will be used to retrieve data
     * @return data received as a String object
     */
    @GetMapping(value = "/schemas/{id}")
    public String listSchemas(@PathVariable(value = "id") Long recordId) {
    	
    	String response = null;
    	
    	if(establishConnection(recordId)) {
    		response = requestData("SELECT s.name AS schema_name, s.schema_id, u.name AS schema_owner FROM sys.schemas s JOIN sys.sysusers u ON u.uid = s.principal_id ORDER BY s.name");
        }

        return response;
    }
    
    /**
     * Lists all tables
     * @param recordId - the ID of a record that will be used to retrieve data
     * @return data received as a String object
     */
    @GetMapping(value = "/tables/{id}")
    public String listTables(@PathVariable(value = "id") Long recordId) {
    	
    	String response = null;
    	
    	if(establishConnection(recordId)) {
    		response = requestData("SHOW tables");
        }

        return response;
    }
    
    /**
     * Lists all columns with additional info
     * @param recordId - the ID of a record that will be used to retrieve data
     * @param tableName - the name of a table to be used
     * @return data received as a String object
     */
    @GetMapping(value = "/columns/{id}/{tableName}")
    public String listColumns(@PathVariable(value = "id") Long recordId,
    							@PathVariable(value = "tableName") String tableName) {
    	
    	String response = null;
    	
    	if(establishConnection(recordId)) {
    		response = requestData("DESCRIBE " + tableName);
        }

        return response;
    }

    /**
     * Shows the whole table 
     * @param recordId - the ID of a record that will be used to retrieve data
     * @param tableName - the name of a table to be used
     * @return data received as a String object
     */
    @GetMapping(value = "/show/{id}/{tableName}")
    public String show(@PathVariable(value = "id") Long recordId,
    							@PathVariable(value = "tableName") String tableName) {
    	
    	String response = null;
    	
    	if(establishConnection(recordId)) {
    		response = requestData("SELECT * FROM " + tableName);
        }

        return response;
    }
    
    /**
     * Gets a record from "connections" table and using the retrieved data sets up a DB connection
     * @param id - ID of the record to be used
     * @return - true if success, false otherwise
     */
    private boolean establishConnection(Long id) { 	
    	try {
    		Record record = connectionRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	    	String request = "jdbc:mysql://" +
	    							record.getHostname() +
	    								":" +
	    									record.getPort() +
	    										"/" + 
	    											record.getDatabasename();	
			connection = DriverManager.getConnection(request, record.getUsername(), record.getPassword());
    		return true;
    	} catch(Exception e) {
    		return false;
    	}
    }

    /**
     * Retrieves data using an SQL query 
     * @param SQLquery - an SQL query to be used 
     * @return returns a string formed from the ResultSet object
     */
    private String requestData(String SQLquery) {
    	try {
    		Statement statement = connection.createStatement();
    		ResultSet rs = statement.executeQuery(SQLquery);
    		String result = "";
    		while(rs.next()) {
    			for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; ++i) {
    				result += rs.getString(i) + '\t';
    			}
    			result += '\n';
    		}
    		return result;
    	} catch (Exception e) {
    		return "Failure!";
    	}
    }
}

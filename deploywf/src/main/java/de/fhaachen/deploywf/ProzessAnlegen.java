package de.fhaachen.deploywf;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Properties;

import utils.*;

public class ProzessAnlegen implements JavaDelegate {


    public void execute(DelegateExecution execution) throws Exception {
    	
    	//prozessvariablen sammeln
    	//exe
    	
    	    
        Connection connect = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
        
    	String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com:3306/dashboardDB";
    	Properties props = new Properties();
    	props.setProperty("user" , Config.getConfig(Config.DB_USER));
    	props.setProperty("password", Config.getConfig(Config.DB_PASS));
    	props.setProperty("useSSL", "false");
    	props.setProperty("autoReconnect", "true");
        	
        try {  
        	execution.setVariable("1", 1);
        	Class.forName("com.mysql.jdbc.Driver");
        	execution.setVariable("2", 1);
        	connect = DriverManager.getConnection(url, props);
        	execution.setVariable("3", 1);
    	}
        catch (SQLException e) {
        	execution.setVariable("err1", e);
        	throw new RuntimeException(e);
            //e.printStackTrace();
        }
        
        try {
	        preparedStatement = connect.prepareStatement("insert into processes (name, description,pic ,warFile,bpmn,added,camunda_processID) values ('T1','T1','Placeholder','Placeholder','Placeholder','2018-12-29','Process_1:1');");
	    	//preparedStatement.setInt(1, Integer.parseInt(delegateTask.getId()));
	        execution.setVariable("4", 1);
	        preparedStatement.executeQuery();
	        execution.setVariable("5", 1);

        
    	} catch (SQLException e) {
    		execution.setVariable("err2", e);
        	throw new RuntimeException(e);
    	}
        
        
        
        
        
    }
}
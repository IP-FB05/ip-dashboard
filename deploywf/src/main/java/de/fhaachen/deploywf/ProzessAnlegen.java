package de.fhaachen.deploywf;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


import java.sql.*;

import java.util.Properties;

import utils.*;

public class ProzessAnlegen implements JavaDelegate {


    public void execute(DelegateExecution execution){

		try{

	    	Connection connect = null;
	    	PreparedStatement preparedStatement = null;
	    	ResultSet resultSet = null;

	    	String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com:3306/dashboardDB";
	    	//String url = "jdbc:mysql://localhost:3306/gpm_server1";   //lokaler server (funktioniert)
	    	
			Properties props = new Properties();
			props.setProperty("user", Config.getConfig(Config.DB_USER));
			props.setProperty("password", Config.getConfig(Config.DB_PASS));
			//props.setProperty("user", "root");						//lokaler server (funktioniert)
			//props.setProperty("password", "gpm");						//lokaler server (funktioniert)
			props.setProperty("useSSL", "false");
			props.setProperty("autoReconnect", "true");

			Class.forName("com.mysql.jdbc.Driver");
			
			//bei dieser zeile wirft er immer einen Fehler (com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Could not create connection to database server. Attempted reconnect 3 times. Giving up.)
			connect = DriverManager.getConnection(url, props);;
			
			//wenn das auskommentiert wird, sollte er einen Datensatz schreiben
			/*	
	        preparedStatement = connect.prepareStatement("insert into processes (name, description,pic ,warFile,bpmn,added,camunda_processID) values ('T1','T1','Placeholder','Placeholder','Placeholder','2018-12-29','Process_1:1');");
	    	//preparedStatement.setInt(1, Integer.parseInt(delegateTask.getId()));
	        preparedStatement.executeQuery();
			*/
			
			//connect.close();
			
			//falls ein Fehler gefangen wird schreibt er den in eine Prozessvariable
    	} catch (SQLException e) {
    		execution.setVariable("err1", e.toString());
        	
    	}catch (Exception e) {
    		execution.setVariable("err2", e.toString());

    	}
        
        

        
        
    }
}
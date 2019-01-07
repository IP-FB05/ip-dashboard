package de.fhaachen.deploywf;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;

import java.sql.*;

import java.util.Properties;

import utils.*;

public class ProzessFreigeben implements JavaDelegate {


    public void execute(DelegateExecution execution){                            
    	
    	
		try {
			Connection connect = null;
	    	PreparedStatement preparedStatement = null;
	    	ResultSet resultSet = null;

	    	String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com:3306/dashboardDB";
	    	//String url = "jdbc:mysql://localhost:3306/dashboardDB"; 
	    	
			Properties props = new Properties();
			props.setProperty("user", Config.getConfig(Config.DB_USER));
			props.setProperty("password", Config.getConfig(Config.DB_PASS));
			//props.setProperty("user", "root");
			//props.setProperty("password", "gpm");
			props.setProperty("useSSL", "false");
			props.setProperty("autoReconnect", "true");

			Class.forName("com.mysql.jdbc.Driver");
			
			connect = DriverManager.getConnection(url, props);
			
			PreparedStatement statement = connect.prepareStatement("UPDATE processes SET `published` = true WHERE processID = ?;");

			statement.setLong(1, Long.parseLong(execution.getVariable("DBID").toString()));		
			statement.executeUpdate();

			connect.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}catch (Exception e) {
		    e.printStackTrace();
		} 
	}

}
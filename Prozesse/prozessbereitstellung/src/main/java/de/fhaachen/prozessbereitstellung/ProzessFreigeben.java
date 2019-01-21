package de.fhaachen.prozessbereitstellung;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import utils.*;

public class ProzessFreigeben implements JavaDelegate {


    public void execute(DelegateExecution execution){                            
    	
    	
		try {
			Connection connect = null;

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
			  //////////////////////////////////////////////////
			 // Ab hier dann Camunda Authorization hinzufügen//
			//////////////////////////////////////////////////
			
			// get Authentication Service
			AuthorizationService authService = execution.getProcessEngineServices().getAuthorizationService();
			
			// get groups
			Collection<String> groups = new ArrayList<String>();
			Authorization newAuth;
			for (String group : groups) {
				// create Authorization
				newAuth = authService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
				
				// set Process as Ressource
				newAuth.setResourceId((String) execution.getVariable("definitionId"));
				
				// set group
				newAuth.setGroupId(group);
				
				// add permissions
				newAuth.addPermission(Permissions.READ_INSTANCE);
				newAuth.addPermission(Permissions.UPDATE_INSTANCE);
				newAuth.addPermission(Permissions.CREATE_INSTANCE);
				newAuth.addPermission(Permissions.TASK_WORK);
				newAuth.addPermission(Permissions.UPDATE_TASK);
				
				// save Authorization
				authService.saveAuthorization(newAuth);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
		}catch (Exception e) {
		    e.printStackTrace();
		} 
	}

}
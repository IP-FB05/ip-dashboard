package de.fhaachen.deploywf;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;

import java.sql.*;

import java.util.Properties;

import utils.*;

public class ProzessAnlegen implements JavaDelegate {


    public void execute(DelegateExecution execution){

    	String name = execution.getVariable("name").toString();
    	String beschreibung = execution.getVariable("beschreibung").toString();
    	String pic = execution.getVariable("pic").toString();
    	String warFile_path = execution.getVariable("warFile_path").toString();
    	String bpmn = execution.getVariable("bpmn").toString();
    	String camunda_processID = execution.getVariable("camunda_processID").toString();
    	String datum = DateTime.now().year().getAsString() + "-" + DateTime.now().monthOfYear().getAsString()  + "-" + DateTime.now().dayOfMonth().getAsString();
    	String ersteller = execution.getVariable("ersteller").toString();
    	
    	execution.setVariable("datum", datum);
		try{

	    	Connection connect = null;
	    	PreparedStatement preparedStatement = null;
	    	ResultSet resultSet = null;

	    	String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com:3306/dashboardDB";
	    	
			Properties props = new Properties();
			props.setProperty("user", Config.getConfig(Config.DB_USER));
			props.setProperty("password", Config.getConfig(Config.DB_PASS));
			props.setProperty("useSSL", "false");
			props.setProperty("autoReconnect", "true");

			Class.forName("com.mysql.jdbc.Driver");
			
			connect = DriverManager.getConnection(url, props);
			
			PreparedStatement statement = connect.prepareStatement("insert into processes (name, description,pic ,warFile,bpmn,added,camunda_processID,creator) values (?,?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, name);
			statement.setString(2, beschreibung);
			statement.setString(3, pic);
			statement.setString(4, warFile_path);
			statement.setString(5, bpmn);
			statement.setString(6, datum);
			statement.setString(7, camunda_processID);
			statement.setString(8, ersteller);
						
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			int key = keys.getInt(1);
			keys.close();


			execution.setVariable("DBID", key);
			connect.close();
			
    	} catch (SQLException e) {
    		execution.setVariable("err1", e.toString());
        	
    	}catch (Exception e) {
    		execution.setVariable("err2", e.getMessage());
    	    e.printStackTrace();
    	} 
    }
}
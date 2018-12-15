package notificationservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.context.Context;


public class NotificationTaskListener implements TaskListener {

  private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
  public static List<String> assigneeList = new ArrayList<String>();

  private static NotificationTaskListener instance = null;

  protected NotificationTaskListener() { }

  public static NotificationTaskListener getInstance() {
    if(instance == null) {
      instance = new NotificationTaskListener();
    }
    return instance;
  }

  public void notify(DelegateTask delegateTask){
    delegateTask.getProcessDefinitionId();
    delegateTask.getProcessInstanceId();
    //1. in der DB schauen, ob dieser Task notifikationswürdig ist, falls nein, abbrechen <-- Nicht mehr!!
    
  //TODO!!!
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
		connect = DriverManager.getConnection(url, props);
		Class.forName("com.mysql.jdbc.Driver");
		
		
	} catch (SQLException e) {
		e.printStackTrace();
	}catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
	
    //2. alle subscriber der prozessdefinition aus der DB holen 
 	List<String> subscriber_list = new ArrayList<String>();

	try {
		preparedStatement = connect.prepareStatement("SELECT ps.subs_subID FROM dashboardDB.processes_has_subs AS ps JOIN "
												+ "dashboardDB.processes_has_process_instance AS ppi "
												+ "on ps.processes_processID = ppi.processes_processID "
												+ "and ppi.process_instance_instanceID = ?");
		preparedStatement.setInt(1, Integer.parseInt(delegateTask.getId()));
		resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			subscriber_list.add(String.valueOf(resultSet.getInt(1)));
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	//3. alle prozessbeteiligten der Prozessinstanz aus der DB holen
	List<String> processbeteiligte_list = new ArrayList<String>();

	try {
		preparedStatement = connect.prepareStatement("SELECT subs_subID FROM dashboardDB.process_instance_has_subs WHERE process_instance_instanceID = ?");
		preparedStatement.setInt(1, Integer.parseInt(delegateTask.getId()));
		resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			processbeteiligte_list.add(String.valueOf(resultSet.getInt(1)));
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
    
    //4. allen beteiligten und subscribern nachrichten verschicken
	//Mail über den Camunda identification service
	String subscriber_msg = "The defined process " +  delegateTask.getProcessDefinitionId()
							+ " has a Task " + delegateTask.getName();
	
	for(String process_subscriber_id : subscriber_list) {
		//Send mail to the subscriber of the process definition
		sendMail(delegateTask, process_subscriber_id, subscriber_msg);
	}
	
	String beteiligte_msg = "Please coplete the Task " +  delegateTask.getName() 
	+ " of the process instance " +  delegateTask.getProcessInstanceId();

	
	for(String process_beteiligte_id :  processbeteiligte_list) {
		
		//Send mail to the participants of the process intance
		sendMail(delegateTask, process_beteiligte_id, beteiligte_msg);
	}
	
	
  }
  
  public void sendMail(DelegateTask delegateTask, String assignee, String msg) {
	  
	//Set Mail Server Properties
	  final String HOST = "smtp.gmail.com";
	  final String USER = "testip@gmail.com";
	  final String PWD = "TestIp2018@";
	  
	  if (assignee != null) {
	      
	      // Get User Profile from User Management
	      IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
	      User user = identityService.createUserQuery().userId(assignee).singleResult();

	      if (user != null) {
	        
	        // Get Email Address from User Profile
	        String recipient = user.getEmail();
	        
	        if (recipient != null && !recipient.isEmpty()) {

	          Email email = new SimpleEmail();
	          email.setCharset("utf-8");
	          email.setHostName(HOST);
	          email.setAuthentication(USER, PWD);

	          try {
	            email.setFrom("noreply@camunda.org");
	            email.setSubject("Task assigned: " + delegateTask.getName());
	            email.setMsg(msg);

	            email.addTo(recipient);

	            email.send();
	            LOGGER.info("Task Assignment Email successfully sent to user '" + assignee + "' with address '" + recipient + "'.");            

	          } catch (Exception e) {
	            LOGGER.log(Level.WARNING, "Could not send email to assignee", e);
	          }

	        } else {
	          LOGGER.warning("Not sending email to user " + assignee + "', user has no email address.");
	        }

	      } else {
	        LOGGER.warning("Not sending email to user " + assignee + "', user is not enrolled with identity service.");
	      }

	    }
  }
	  
  }
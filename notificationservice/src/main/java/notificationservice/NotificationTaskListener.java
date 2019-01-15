package notificationservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Config;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.*;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class NotificationTaskListener implements TaskListener {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	public static List<String> assigneeList = new ArrayList<String>();

	private static NotificationTaskListener instance = null;

	protected NotificationTaskListener() {
	}

	public static NotificationTaskListener getInstance() {
		if (instance == null) {
			instance = new NotificationTaskListener();
		}
		return instance;
	}

	public void notify(DelegateTask delegateTask) {
		String processDefinitionID = delegateTask.getProcessDefinitionId();
		String processInstanceID = delegateTask.getProcessInstanceId();

		// 1. open DB connection
		String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com/dashboardDB";
		Properties props = new Properties();
		props.setProperty("user", Config.getConfig(Config.DB_USER));
		props.setProperty("password", Config.getConfig(Config.DB_PASS));
		props.setProperty("useSSL", "false");
		props.setProperty("autoReconnect", "true");

		Connection connect = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, props);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2. alle subscriber der prozessdefinition aus der DB holen
		List<String> subscriber_list = new ArrayList<String>();

		try {
			PreparedStatement preparedStatement = connect.prepareStatement(
					"select s.username from processes as p join processes_has_subs as ps on p.processID = ps.processes_processID join subs as s on s.subID = ps.subs_subID where camunda_processID = ? ");
			String defID = processDefinitionID.split(":")[0];
			preparedStatement.setString(1, processDefinitionID);
			ResultSet resultSet;
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				subscriber_list.add(resultSet.getString(1));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 3. alle prozessbeteiligten der Prozessinstanz aus der DB holen
		List<String> processbeteiligte_list = new ArrayList<String>();

		try {
			PreparedStatement preparedStatement = connect.prepareStatement(
					"select s.username from process_instance as p join process_instance_has_subs as ps on p.instanceID = ps.process_instance_instanceID join subs as s on s.subID = ps.subs_subID where camunda_instanceID = ? ");
			preparedStatement.setString(1, processInstanceID);
			ResultSet resultSet;
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				processbeteiligte_list.add(resultSet.getString(1));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		if (processbeteiligte_list.size() > 0 || subscriber_list.size() > 0) {
			
			// 4. email addressen besorgen
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			String cslDestination = "";
			for (String subscriber : subscriber_list) {
				cslDestination = cslDestination + processEngine.getIdentityService().createUserQuery()
						.userId(subscriber).singleResult().getEmail() + ",";
			}
			for (String subscriber : processbeteiligte_list) {
				cslDestination = cslDestination + processEngine.getIdentityService().createUserQuery()
						.userId(subscriber).singleResult().getEmail() + ",";
			}
			cslDestination = cslDestination.substring(0, cslDestination.length() - 1);

			// 5. Jetzt noch den bearbeiter (falls vorhanden und nicht deaktiviert)
			String asignee = delegateTask.getAssignee();
			boolean notify = true;
			if(asignee != null) {
				try {
					PreparedStatement preparedStatement = connect.prepareStatement("select * from notification where username = ?");
					preparedStatement.setString(1, asignee);
					ResultSet res = preparedStatement.executeQuery();
					if(res.first()) {
						notify = false;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(notify) {
				cslDestination = cslDestination + processEngine.getIdentityService().createUserQuery()
						.userId(asignee).singleResult().getEmail() + ",";
			}
			
			// 6. mail versenden
			final String username = "dashboarddonotreply@gmail.com";
			final String password = Config.getConfig(Config.MAIL_PASS);

			props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			
			// Infos zum Prozess holen
			ProcessDefinition processDefinition = delegateTask.getProcessEngineServices().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionID).singleResult();
			HistoricProcessInstance processInstance = delegateTask.getProcessEngineServices().getHistoryService().createHistoricProcessInstanceQuery().processDefinitionId(processDefinitionID).singleResult();
			
			String processname = processDefinition.getName();
			User processowner = delegateTask.getProcessEngineServices().getIdentityService().createUserQuery().userId(processInstance.getStartUserId()).singleResult();
			String started = delegateTask.getProcessEngineServices().getHistoryService().createHistoricActivityInstanceQuery().activityId(processInstance.getStartActivityId()).singleResult().getStartTime().toString();
			
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("dashboarddonotreply@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cslDestination));
				message.setSubject("Task \"" + delegateTask.getName() + "\" wurde erreicht");
				
				String text = "Der Prozess \"" + processname + "\", gestartet am " + started + " von " + processowner + " hat den Task " + delegateTask.getName() + " erreicht.";
				if(asignee != null) {
					text += "Die Bearbeitung wurde " + asignee + " zugewiesen.";
				}
				message.setText(text);
				Transport.send(message);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
package de.fhaachen.ip.Pruefungsdurchfuehrung;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.Config;



public class SendEmail implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
	
    	final String username = "dashboarddonotreply@gmail.com";
		final String password = Config.getConfig(Config.MAIL_PASS);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

        try {

        	Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("dashboarddonotreply@gmail.com"));
			
			
			String subject = execution.getVariable("subject").toString();
            message.setSubject(subject);

            String text = execution.getVariable("text").toString();
            message.setText(text);
			

            //Get zielEmail
            
			String[][] students = (String[][]) execution.getVariable("studRESTRe");
            
            String destination = "";
            
			for(int i = 0; i < students.length; i++) {
				
				destination = students[i][2].toString();
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
				
            	//Transport.send(message);
            }
			
			String starterstudent = (String) execution.getVariable("initiator");  
            
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();            
            
            destination = processEngine.getIdentityService().createUserQuery()
					.userId(starterstudent).singleResult().getEmail();
            
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
            
            //Transport.send(message);

			
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
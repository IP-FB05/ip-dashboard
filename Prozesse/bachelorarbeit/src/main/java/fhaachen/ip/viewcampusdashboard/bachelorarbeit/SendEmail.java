package fhaachen.ip.viewcampusdashboard.bachelorarbeit;

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

            //Get zielEmail
            
            //starter name
            String emailUser = (String) execution.getVariable("zielEmail");  
            
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();            
            
            String destination = processEngine.getIdentityService().createUserQuery()
					.userId(emailUser).singleResult().getEmail();
            
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
            
            
            String subject = execution.getVariable("subject").toString();
            message.setSubject(subject);

            String text = execution.getVariable("text").toString();
            message.setText(text);

            //Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
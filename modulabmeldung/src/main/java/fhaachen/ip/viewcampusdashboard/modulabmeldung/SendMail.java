package fhaachen.ip.viewcampusdashboard.modulabmeldung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {  
	      String to = execution.getVariable("targetEmail").toString();

	      String from = "ViewCampus-Dashboard@gmx.net";
	      final String username = "ViewCampus-Dashboard@gmx.net";
	      final String password = "Dashboard";
	      String host = "mail.gmx.net";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "25");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
		   }
	         });

	      try {
		   Message message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(from));
		   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

		   String subject = execution.getVariable("subjectInput").toString();
		   message.setSubject(subject);

		   String messageString = execution.getVariable("messageInput").toString();
		   message.setText(messageString);

		   Transport.send(message);
	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }

	}

}

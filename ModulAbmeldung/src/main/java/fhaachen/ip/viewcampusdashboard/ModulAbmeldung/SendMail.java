package fhaachen.ip.viewcampusdashboard.ModulAbmeldung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Recipient's email ID needs to be mentioned.
	      String to = execution.getVariable("targetEmail").toString();
	      
	      // Sender's email ID needs to be mentioned
	      String from = "ViewCampus-Dashboard@gmx.net";

	      // Assuming you are sending email from localhost
	      String host = "localhost";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);
	      properties.setProperty("mail.user", "ViewCampus-Dashboard@gmx.net");
	      properties.setProperty("mail.password", "Dashboard");

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         String subject = execution.getVariable("subjectInput").toString();
	         message.setSubject(subject);

	         // Send the actual HTML message, as big as you like
	         String messageString = execution.getVariable("messageInput").toString();
	         message.setContent("<h1>"+messageString+"</h1>", "text/html");

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }

	}

}

package de.fhaachen.praxissemester;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendMailAdapter implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		String to = (String) execution.getVariable("to");
		String subject = (String) execution.getVariable("subject");
		String content = (String) execution.getVariable("content");
		Boolean auszeichnung = false;
		try {
			auszeichnung = (Boolean) execution.getVariable("result");
			if(!auszeichnung) {
				auszeichnung = false;
			}
		} catch (Exception e) {
			auszeichnung = false;
		}

		// Sender's email ID needs to be mentioned
		String from = "meinmailapitestadresse@gmail.com";// change accordingly
		final String username = "meinmailapitestadresse";// change accordingly
		final String password = "GPMgruppe10";// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			Multipart mult = new MimeMultipart();

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(content);
			mult.addBodyPart(messageBodyPart);

			if (auszeichnung) {
				messageBodyPart = new MimeBodyPart();
				String filename = "/home/gpm17/Dokumente/Zeugnis.pdf";
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename);
				mult.addBodyPart(messageBodyPart);
			}

			message.setContent(mult);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}

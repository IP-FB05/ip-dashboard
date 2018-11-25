package de.fhaachen.praxissemester;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class SendMailAdapter implements JavaDelegate{
    public void execute(DelegateExecution execution) throws Exception {

        final String username = Config.getConfig(Config.MAIL_ADR);
        final String password = Config.getConfig(Config.MAIL_PASS);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dashboarddonotreply@gmail.com"));

            String ziel = execution.getVariable("to").toString();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ziel));

            String subject = execution.getVariable("subject").toString();
            message.setSubject(subject);

            String text = execution.getVariable("content").toString();
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}

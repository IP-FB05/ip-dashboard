package de.fhaachen.ip.Pruefungsdurchfuehrung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import utils.Config;
import utils.GetStudents;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;



public class SendEmail implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {

    	String[][] students = GetStudents.Get(execution.getVariable("modul").toString());
    	
    	
    	
        final String username = "dashboarddonotreply@gmail.com";
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

            String ziel = execution.getVariable("zielEmail").toString();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ziel));

            String subject = execution.getVariable("subject").toString();
            message.setSubject(subject);

            String text = execution.getVariable("text").toString();
            message.setText(text);

            
            for(int i = 0;i < students.length;i++) {
            	message.setText(students[i][2]);
            	Transport.send(message);
            }
            


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
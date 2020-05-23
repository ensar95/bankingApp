package com.banking.app.bankingApp.service.email;

import com.banking.app.bankingApp.config.SecurityConstants;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;

import java.util.Date;


public class EmailManagementService {
    private static EmailManagementService emailManagementService;

    public static EmailManagementService getInstance() {
        if (emailManagementService == null) {
            emailManagementService = new EmailManagementService();
        }
        return emailManagementService;
    }

    public void sendEmail() throws AddressException, MessagingException, IOException {
        String host = "smtp.gmail.com";
        String port = "587";
        try {
            java.util.Properties props = null;
            props = System.getProperties();
            props.put("mail.smtp.user", SecurityConstants.EMAIL_USERNAME);
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.socketFactory.port", port);


            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("bankingappensarskopljak@gmail.com"));
            msg.setSubject("subject");
            msg.setText("This mail has been automatically sent from BankingApp", "ISO-8859-1");
            msg.setSentDate(new Date());
            msg.setHeader("content-Type", "text/html;charset=\"ISO-8859-1\"");
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("Haris.orucevic@hotmail.com"));
            msg.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(host, SecurityConstants.EMAIL_USERNAME, SecurityConstants.EMAIL_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


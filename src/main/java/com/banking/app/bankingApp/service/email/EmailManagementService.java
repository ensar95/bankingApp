package com.banking.app.bankingApp.service.email;

import com.banking.app.bankingApp.config.SecurityConstants;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class EmailManagementService {

    public void sendVerificationEmail(String email, String code) throws AddressException, MessagingException, IOException {
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
            msg.setSubject("Do not reply! Please verify your email!");
            msg.setText("This mail has been automatically sent from BankingApp. " +
                    "\nPlease verify your email using this code: " + code, "ISO-8859-1");
            msg.setSentDate(new Date());
            msg.setHeader("content-Type", "text/html;charset=\"ISO-8859-1\"");
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(host, SecurityConstants.EMAIL_USERNAME, SecurityConstants.EMAIL_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateCode() {
        int length = 8;
        final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        List<Character> code = new ArrayList<>();
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return stringBuffer.toString();
    }
}


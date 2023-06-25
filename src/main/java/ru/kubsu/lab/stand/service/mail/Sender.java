package ru.kubsu.lab.stand.service.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Sender {


    private final String from = "a.gadjiev1@mail.ru";
    private final String pass = "a37Lj2ZNvQFkfPLESsya";
    private final String host = "smtp.mail.ru";

    private final String smtpPort = "465";

    private final Properties properties = new Properties();

    public Sender() {

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");


    }


    public void send(String to, String subject, String text) {

        Session session = Session.getInstance(
                properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, pass);
                    }
                }

        );

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject);

            message.setText(text);

            // Send message
            Transport.send(message);
            System.out.println("Письмо отправлено");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }


}

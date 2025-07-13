package com.example.springboot.SpringBootTestConsumer.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Компонент содержащий логику отправки писем
 */
@Component
public class EmailSenderService {
    private final String SUBJECT_SUCCESSFUL_CREATION = "Успешное создание аккаунта";
    private final String SUBJECT_SUCCESSFUL_DELETION = "Удаление аккаунта";
    private final String TEXT_SUCCESSFUL_CREATION = "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";
    private final String TEXT_SUCCESSFUL_DELETION = "Здравствуйте! Ваш аккаунт был удалён.";

    private final String from = "sender@abc.com";// sender email
    private final String host = "127.0.0.1";// mail server host
    private final String port = "2500"; //port

    /**
     * Отправляет на полученный email сообщение об успешном создании аккаунта
     */
    public void sendCreateEmail(String userEmail) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);

        Session session = Session.getDefaultInstance(properties); // default session

        try {
            MimeMessage message = new MimeMessage(session); // email message

            message.setFrom(new InternetAddress(from)); // setting header fields
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(SUBJECT_SUCCESSFUL_CREATION); // subject line
            message.setText(TEXT_SUCCESSFUL_CREATION);// actual mail body

            // Send message
            Transport.send(message);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    /**
     * Отправляет на полученный email сообщение об успешном удалении аккаунта
     */

    public void sendDeleteEmail(String userEmail) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);

        Session session = Session.getDefaultInstance(properties); // default session

        try {
            MimeMessage message = new MimeMessage(session); // email message

            message.setFrom(new InternetAddress(from)); // setting header fields
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(SUBJECT_SUCCESSFUL_DELETION); // subject line
            message.setText(TEXT_SUCCESSFUL_DELETION);// actual mail body

            // Send message
            Transport.send(message);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
//
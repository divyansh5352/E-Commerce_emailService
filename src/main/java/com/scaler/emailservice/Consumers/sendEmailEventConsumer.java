package com.scaler.emailservice.Consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.emailservice.DTO.sendEmailEventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class sendEmailEventConsumer {
    private ObjectMapper objectMapper;
    public sendEmailEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //if there are multiple instance of emailService
    // using this group id kafka will get to know that
    // all the instance with this groupId are same
    @KafkaListener(topics = "SendEmail", groupId = "EmailService")
    public void handelSendEmailEvent(String message ) throws JsonProcessingException {

        sendEmailEventDTO eventDTO = objectMapper.readValue(message , sendEmailEventDTO.class);

        String to = eventDTO.getTo();
        String from = eventDTO.getFrom();
        String subject = eventDTO.getSubject();
        String body = eventDTO.getBody();


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ProjectEmailService5352@gmail.com", "xnfalqpydxpesist" );
            }
        };
        Session session = Session.getInstance(props, auth);

        com.journaldev.mail.EmailUtil.sendEmail(session, to,subject, body);
    }
}

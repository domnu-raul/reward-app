package com.rewardapp.backend.services;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.Map;
import java.util.Properties;

@Service
@Transactional
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        Map<String, Object> map = null;
        try {
            JSONParser parser = new JSONParser(new FileReader("src/main/resources/email.json"));
            map = parser.parseObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> body = (Map<String, String>) map.get("email");

        String username = body.get("username");
        String password = body.get("password");

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("reward.app.smtp@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        this.mailSender.send(message);
    }
}

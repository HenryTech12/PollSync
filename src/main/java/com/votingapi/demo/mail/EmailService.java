package com.votingapi.demo.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String from;
    public void sendEmail(String to, String body, String subject) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }

    public void sendWelcomeMessage(String to, String subject) {

    }

    public void sendHTMLFile(String to, String subject, String filename) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from);

            var inputStream = Objects.requireNonNull(EmailService.class.getResourceAsStream("/templates/"+filename+".html"));
            mimeMessageHelper.setText(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),true);

            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException  | IOException exception) {
            logger.error(exception.getMessage());
        }
    }
}

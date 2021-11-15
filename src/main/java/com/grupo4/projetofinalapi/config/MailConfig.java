package com.grupo4.projetofinalapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Configuration
public class MailConfig {
    @Autowired
    public JavaMailSender javaMailSender;

    public void sendMail(String para, String assunto, String conteudo) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom("***REMOVED***");
        helper.setTo(para);
        helper.setSubject(assunto);
        helper.setText(conteudo, true);
        javaMailSender.send(message);
    }
}

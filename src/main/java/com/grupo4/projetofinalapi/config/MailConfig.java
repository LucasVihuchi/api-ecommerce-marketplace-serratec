package com.grupo4.projetofinalapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/** Classe de configuração do sistema de envio de emails
 */
@Configuration
public class MailConfig {
    @Autowired
    public JavaMailSender javaMailSender;

    /** Método para enviar um email formatado em HTML para um destinatário
     *
     * @param para endereço de email que irá receber o email
     * @param assunto campo assunto do email
     * @param conteudo corpo da mensagem que irá compor o email
     * @throws MessagingException
     */
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

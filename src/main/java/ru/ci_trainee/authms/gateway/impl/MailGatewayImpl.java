package ru.ci_trainee.authms.gateway.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.ci_trainee.authms.constants.MailConstants;
import ru.ci_trainee.authms.exception.exception.MailSendingException;
import ru.ci_trainee.authms.gateway.MailGateway;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailGatewayImpl implements MailGateway {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    @Override
    public void send(String toEmail, String html) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, MailConstants.MIME_ENCODING);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(MailConstants.RESET_PASSWORD_SUBJECT);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException exception) {
            log.error("Message hasn't been sent. {}", exception.getMessage());
            throw new MailSendingException("Не удалось отправить письмо");
        }
    }
}

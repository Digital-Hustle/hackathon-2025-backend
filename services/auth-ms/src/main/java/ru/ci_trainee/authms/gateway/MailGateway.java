package ru.ci_trainee.authms.gateway;

public interface MailGateway {

    void send(String toEmail, String html);
}

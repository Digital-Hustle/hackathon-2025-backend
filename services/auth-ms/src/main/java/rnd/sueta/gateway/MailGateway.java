package rnd.sueta.gateway;

public interface MailGateway {

    void send(String toEmail, String html);
}

package ecomarkets.domain.notification.email;

import ecomarkets.domain.register.EmailAddress;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.util.Optional;

public class Email extends PanacheEntity {
    private EmailAddress from;
    private EmailAddress to;
    private String subject;
    private String body;
    private String messageIdSent;

    public Email(EmailAddress from, EmailAddress to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public EmailAddress getFrom() {
        return from;
    }

    public EmailAddress getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getMessageIdSent() {
        return messageIdSent;
    }

    public void send(Optional<String> messageIdSent) {
        this.messageIdSent = messageIdSent.orElse(null);
    }
}

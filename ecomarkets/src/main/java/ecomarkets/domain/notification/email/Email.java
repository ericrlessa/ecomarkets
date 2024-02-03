package ecomarkets.domain.notification.email;

import ecomarkets.domain.register.EmailAddress;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Email extends PanacheEntity {

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email_from"))
    })
    private EmailAddress from;
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email_to"))
    })
    private EmailAddress to;
    private String subject;
    private String body;

    private Email(){}

    public Email(EmailAddress from, EmailAddress to, String subject, String body) {
        this();
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

}

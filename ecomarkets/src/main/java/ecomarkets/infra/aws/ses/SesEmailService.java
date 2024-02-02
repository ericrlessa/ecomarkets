package ecomarkets.infra.aws.ses;

import ecomarkets.domain.notification.email.Email;
import ecomarkets.domain.notification.email.EmailNotificationService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SesEmailService implements EmailNotificationService {

    @Override
    public void send(Email email) {
        email.persist();
    }
}

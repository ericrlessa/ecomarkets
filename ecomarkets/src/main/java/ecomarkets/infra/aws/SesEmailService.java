package ecomarkets.infra.aws;

import ecomarkets.domain.notification.email.Email;
import ecomarkets.domain.notification.email.EmailNotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.ses.SesClient;

@ApplicationScoped
public class SesEmailService implements EmailNotificationService {

    @Inject
    SesClient ses;

    @Override
    public String send(Email email) {
        return ses.sendEmail(req -> req
                .source(email.from().value())
                .destination(d -> d.toAddresses(email.to().value()))
                .message(msg -> msg
                        .subject(sub -> sub.data(email.subject()))
                        .body(b -> b.text(txt -> txt.data(email.body()))))).messageId();
    }
}

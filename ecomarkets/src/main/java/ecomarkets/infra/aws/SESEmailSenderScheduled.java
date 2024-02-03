package ecomarkets.infra.aws;

import ecomarkets.domain.notification.email.Email;
import ecomarkets.domain.notification.email.EmailNotificationService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.ses.SesClient;

import java.util.List;

@ApplicationScoped
public class SESEmailSenderScheduled implements EmailNotificationService {

    @Inject
    SesClient ses;

    public void send(Email email) {
        final String messageId = ses.sendEmail(req -> req
                .source(email.getFrom().value())
                .destination(d -> d.toAddresses(email.getTo().value()))
                .message(msg -> msg
                        .subject(sub -> sub.data(email.getSubject()))
                        .body(b -> b.text(txt -> txt.data(email.getBody()))))).messageId();
        //TODO add observability for emails sent and error treatment
    }
}

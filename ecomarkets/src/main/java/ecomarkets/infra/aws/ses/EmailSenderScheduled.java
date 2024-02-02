package ecomarkets.infra.aws.ses;

import ecomarkets.domain.notification.email.Email;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.ses.SesClient;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EmailSenderScheduled {

    @Inject
    SesClient ses;

    @Scheduled(every="2m")
    public void sendPendingEmails(Email email) {
        List<Email> emailList = Email.list("messageIdSent", (Object) null);

        emailList.stream().forEach(e -> {
                e.send(send(e));
        });
    }

    private Optional<String> send(Email email) {
        try{
            final String messageId = ses.sendEmail(req -> req
                .source(email.getFrom().value())
                .destination(d -> d.toAddresses(email.getTo().value()))
                .message(msg -> msg
                        .subject(sub -> sub.data(email.getSubject()))
                        .body(b -> b.text(txt -> txt.data(email.getBody()))))).messageId();
            return Optional.of(messageId);
        }catch (RuntimeException exception){
            return Optional.empty();
        }
    }
}

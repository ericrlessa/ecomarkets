package ecomarkets.domain.notification.email;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class EmailSenderScheduled {

    @Inject
    EmailNotificationService emailNotificationService;

    @Scheduled(every="2m")
    public void sendPendingEmails() {
        List<Email> emailList = Email.findAll().list();

        emailList.stream().forEach(e ->{
            emailNotificationService.send(e);
            e.delete();
        });
    }

}

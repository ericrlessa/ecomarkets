package ecomarkets.infra.aws;

import ecomarkets.domain.notification.email.Email;
import ecomarkets.domain.register.EmailAddress;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.VerifyEmailIdentityRequest;
@QuarkusTest
public class SESEmailSenderTest {

    @ConfigProperty(name = "email.from.notification")
    private String emailFrom;
    @Inject
    SESEmailSender sesEmailSender;
    @Inject
    SesClient ses;
    @TestTransaction
    @Test
    public void testSendEmail(){

        VerifyEmailIdentityRequest request = VerifyEmailIdentityRequest.builder()
                .emailAddress(emailFrom)
                .build();

        ses.verifyEmailIdentity(request);

        Email email = new Email(EmailAddress.of(emailFrom),
                EmailAddress.of("test@ecomarkets.com"),
                "Test email notification",
                " This is a email body!"
        );

        sesEmailSender.send(email);
    }

}


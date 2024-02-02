package ecomarkets.domain.notification.email;

import ecomarkets.domain.register.EmailAddress;

public record Email(EmailAddress from, EmailAddress to, String subject, String body) {
}

package com.kemenu.kemenu_backend.application.email;

import com.kemenu.kemenu_backend.domain.event.ConfirmationEmailEvent;
import com.kemenu.kemenu_backend.domain.event.SendEmailEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class EmailEventFactory {

    private final MessageSource messageSource;

    public ConfirmationEmailEvent confirmationEmailEvent(String lang, String email) {
        return ConfirmationEmailEvent.builder()
                .sendEmailEvent(
                        createNoReplyEmail(
                                lang,
                                "email.confirmation.subject",
                                "email.confirmation.content",
                                email
                        )
                )
                .build();
    }

    private SendEmailEvent createNoReplyEmail(String lang, String subjectMessage, String contentMessage, String email) {
        Locale locale = new Locale.Builder().setLanguage(lang).build();
        String subject = messageSource.getMessage(subjectMessage, null, locale);
        String content = messageSource.getMessage(contentMessage, null, locale);
        return SendEmailEvent.noReplyEmail(email, subject, content);
    }
}

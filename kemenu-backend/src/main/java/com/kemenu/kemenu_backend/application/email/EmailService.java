package com.kemenu.kemenu_backend.application.email;

import com.kemenu.kemenu_backend.domain.event.ConfirmationEmailEvent;
import com.kemenu.kemenu_backend.domain.event.SendEmailEvent;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${app.sendgrid}")
    private String sendgridApiKey;

    private final MessageSource messageSource;

    @SneakyThrows
    public void sendMail(SendEmailEvent event, String contentWithUrl) {
        Email from = new Email(event.getFrom());
        String subject = event.getSubject();
        Email to = new Email(event.getTo());
        Content content = new Content("text/plain", contentWithUrl);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);

        if (response.getStatusCode() > 299) {
            log.warn("Email sent failed with status code {}, body {} and headers {}", response.getStatusCode(), response.getBody(), response.getHeaders());
        }
    }

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

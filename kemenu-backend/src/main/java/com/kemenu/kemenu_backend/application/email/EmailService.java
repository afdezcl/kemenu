package com.kemenu.kemenu_backend.application.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class EmailService {

    @Value("${app.sendgrid}")
    private String sendgridApiKey;

    // @EventListener(ApplicationReadyEvent.class)
    public void sendMail() throws IOException {
        Email from = new Email("noreply@kemenu.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("nvortega92@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        log.info("Email sent with status code {} and body {} and headers {}", response.getStatusCode(), response.getBody(), response.getHeaders());
    }
}

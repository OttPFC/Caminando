package com.caminando.Caminando.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class EmailConfig {
    private final SendGrid sg;

    public EmailConfig(@Value("${SENDGRID_API_KEY}") String apiKey) {
        if (apiKey == null) {
            throw new IllegalArgumentException("API key must not be null");
        }
        this.sg = new SendGrid(apiKey);
    }

    public void sendMail(String recipientEmail, String subject, String content) {
        Email from = new Email("ale.maz097@gmail.com");
        Email to = new Email(recipientEmail);
        Content contentObj = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, to, contentObj);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Email sent status: " + response.getStatusCode());
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("Failed to send email", ex);
        }
    }
}


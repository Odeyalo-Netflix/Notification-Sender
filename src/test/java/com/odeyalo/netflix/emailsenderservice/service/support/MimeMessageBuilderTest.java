package com.odeyalo.netflix.emailsenderservice.service.support;

import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class MimeMessageBuilderTest {

    @Test
    void buildMimeMessage() throws MessagingException, IOException {
        MimeMessageBuilder mimeMessageBuilder = new MimeMessageBuilder(Session.getInstance(new Properties()));
        MimeMessage message = mimeMessageBuilder.buildMimeMessage(new EmailMessageDTO("to", "body", "subject"));
        Address[] allRecipients = message.getAllRecipients();
        System.out.println(Arrays.toString(allRecipients));
        System.out.println(message.getContent());
    }

    @Test
    void testBuildMimeMessage() {
    }
}

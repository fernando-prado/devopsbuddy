package com.devopsbuddy.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Real implementation of an email service.
 *
 */
public class SmtpEmailService extends AbstractEmailService {

    /** The application logger */
    private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage message) {
        log.debug("Sending email for: {}", message);
        mailSender.send(message);
        log.info("Email sent.");
    }
}
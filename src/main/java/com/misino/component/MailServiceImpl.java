package com.misino.component;


import com.misino.template.mail.MailTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component("mailService")
@Scope("singleton")
public class MailServiceImpl extends Thread
{
    static final Logger log = LoggerFactory.getLogger( MailServiceImpl.class );

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    MessageSource messageSource;

    BlockingQueue<MailTemplate> messageQueue = new LinkedBlockingQueue<MailTemplate>(10) {};

    public MailServiceImpl() {
        this.start();
    }

    @Override
    public void run() {
        log.info("Starting mail sender component");
        while(true) {
            try
            {
                MailTemplate messageTemplate = messageQueue.take();
                try {
                    MimeMessage mimeMessage = this.mailSender.createMimeMessage();
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, messageTemplate.isMultipart(), "UTF-8");
                    String subject = messageSource.getMessage(messageTemplate.getSubject(), null, messageTemplate.getTemplateContext().getLocale());
                    message.setSubject(subject);
                    message.setTo(messageTemplate.getRecipient());
                    message.setFrom("birosh@gmail.com");
                    String htmlContent = templateEngine.process(messageTemplate.getTemplatePath(), messageTemplate.getTemplateContext());
                    message.setText(htmlContent, true);
                    mailSender.send(mimeMessage);
                } catch (MailParseException e) {
                    log.error( String.format( "Mail with recipient '%' could not be parsed" ) );
                } catch (MailSendException e) {
                    log.error( String.format( "Mail with recipient '%' could not be sent" ) );
                } catch (MailException e) {
                    log.error( String.format( "Sending mail to recipient '%' ended with exception" ) );
                } catch (MessagingException e) {
                    log.error(String.format("Multipart creation failed for message '%', recipient '%'", messageTemplate.getTemplatePath(), messageTemplate.getRecipient()));
                }

            } catch ( InterruptedException e )
            {
                log.error( "Interrupted exception" );
            }
        }
    }

    public void addToQueue(MailTemplate message) {
        messageQueue.add( message );
    }
}

package com.misino.config;

import com.misino.component.MailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig
{
    @Resource
    Environment env;

    private static final String MAIL_HOST = "mail.server.host";
    private static final String MAIL_PORT = "mail.server.port";
    private static final String MAIL_PROTOCOL = "mail.server.protocol";
    private static final String MAIL_USERNAME = "mail.server.username";
    private static final String MAIL_PASSWORD = "mail.server.password";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";

    @Bean
    MailServiceImpl mailService() {
        return new MailServiceImpl();
    }

    @Bean
    JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost( env.getRequiredProperty( MAIL_HOST ) );
        mailSender.setPort( env.getRequiredProperty( MAIL_PORT, Integer.class ) );
        mailSender.setProtocol( env.getRequiredProperty( MAIL_PROTOCOL ) );
        mailSender.setUsername( env.getRequiredProperty( MAIL_USERNAME ) );
        mailSender.setPassword( env.getRequiredProperty( MAIL_PASSWORD ) );

        Properties properties = new Properties(  );
        properties.setProperty( MAIL_SMTP_AUTH, env.getProperty( MAIL_SMTP_AUTH ) );
        properties.setProperty( MAIL_SMTP_STARTTLS, env.getProperty(MAIL_SMTP_STARTTLS) );
        mailSender.setJavaMailProperties( properties );
        return mailSender;
    }
}

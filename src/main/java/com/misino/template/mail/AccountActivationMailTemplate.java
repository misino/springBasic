package com.misino.template.mail;

import com.misino.controller.AuthenticationController;
import org.thymeleaf.context.Context;

import java.util.Locale;

public class AccountActivationMailTemplate implements MailTemplate {
    private Context templateContext;
    private String recipient;
    private String pageUrl = "http://localhost:8081";

    public AccountActivationMailTemplate(String recipientEmail, String accountActivationKey, Locale locale) {
        this.recipient = recipientEmail;
        this.templateContext = new Context(locale);
        String activationLink = pageUrl + AuthenticationController.LINK_AUTH_CONTROLLER +AuthenticationController.LINK_ACTIVATION + "?email="+recipientEmail+"&key="+accountActivationKey;
        templateContext.setVariable("activationLink", activationLink);
    }

    public String getTemplatePath() {
        return "accountActivation";
    }

    public Context getTemplateContext() {
        return templateContext;
    }

    public String getRecipient() {
        return recipient;
    }

    public boolean isMultipart() {
        return false;
    }

    public String getSubject() { return "email.registerSuccess.subject"; }
}

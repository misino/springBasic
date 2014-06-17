package com.misino.template.mail;

import com.misino.controller.AuthenticationController;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ForgottenPasswordMailTemplate implements MailTemplate {
    private Context templateContext;
    private String recipient;
    private String pageUrl = "http://localhost:8081";

    public ForgottenPasswordMailTemplate(String recipientEmail, String accountActivationKey, Integer hoursLinkActive, Locale locale) {
        this.recipient = recipientEmail;
        this.templateContext = new Context(locale);
        String activationLink = pageUrl + AuthenticationController.LINK_AUTH_CONTROLLER + AuthenticationController.LINK_RESET_PASSWORD + "?email="+recipientEmail+"&key="+accountActivationKey;
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("activationLink", activationLink);
        variables.put("hoursLinkActive", hoursLinkActive.toString());
        templateContext.setVariables(variables);
    }

    public String getTemplatePath() {
        return "forgottenPassword";
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

    public String getSubject() { return "email.forgottenPassword.subject"; }
}

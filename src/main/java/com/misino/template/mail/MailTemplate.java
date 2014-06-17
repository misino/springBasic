package com.misino.template.mail;

import org.thymeleaf.context.Context;

public interface MailTemplate {
    /**
     * Get name of template file. {@link com.misino.config.TemplatesConfig}
     * @return
     */
    String getTemplatePath();
    Context getTemplateContext();
    String getRecipient();
    boolean isMultipart();

    /**
     * Get localization key for subject
     * @return
     */
    String getSubject();
}

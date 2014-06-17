package com.misino.data.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by misino on 1/1/14.
 */
public class ForgottenPasswordForm
{
    @NotEmpty
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }
}

package com.misino.data.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by misino on 12/28/13.
 */
public class RegisterForm
{
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length( min=6 )
    private String password;
    @NotEmpty
    private String repeatPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword( String repeatPassword ) {
        this.repeatPassword = repeatPassword;
    }
}

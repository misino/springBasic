package com.misino.data.form.validator;

import com.misino.data.form.ResetPasswordForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ResetPasswordFormValidator implements Validator
{
    @Override
    public boolean supports( Class<?> clazz ) {
        return ResetPasswordForm.class.equals( clazz );
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ResetPasswordForm form = (ResetPasswordForm) target;

        if(!errors.hasErrors()) {
            if(!form.getPassword().equals( form.getRepeatPassword()) ) {
                errors.rejectValue( "repeatPassword", "register.error.password.equal" );
            }
        }
    }
}

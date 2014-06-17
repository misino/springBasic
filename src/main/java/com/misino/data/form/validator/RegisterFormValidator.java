package com.misino.data.form.validator;

import com.misino.data.form.RegisterForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegisterFormValidator implements Validator
{
    @Override
    public boolean supports( Class<?> clazz ) {
        return RegisterForm.class.equals( clazz );
    }

    @Override
    public void validate( Object target, Errors errors ) {
        RegisterForm form = (RegisterForm) target;

        if(!errors.hasErrors()) {
            if(!form.getPassword().equals( form.getRepeatPassword()) ) {
                errors.rejectValue( "repeatPassword", "register.error.password.equal" );
            }
        }
    }
}

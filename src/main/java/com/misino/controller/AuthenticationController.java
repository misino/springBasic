package com.misino.controller;

import com.misino.data.domain.User;
import com.misino.data.form.ForgottenPasswordForm;
import com.misino.data.form.LoginForm;
import com.misino.data.form.RegisterForm;
import com.misino.data.form.ResetPasswordForm;
import com.misino.data.form.validator.RegisterFormValidator;
import com.misino.data.form.validator.ResetPasswordFormValidator;
import com.misino.service.UserService;
import com.misino.utils.exceptions.BadAttemptException;
import com.misino.utils.exceptions.UniqueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.http.HTTPException;

@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController extends AbstractBaseController
{

    static final Logger log = LoggerFactory.getLogger( AuthenticationController.class );

    // pages
    private static final String PAGE_BASIC_MESSAGE = "global/messagePage";
    private static final String PAGE_FORGOTTEN_PASSWORD = "security/forgottenPassword";
    private static final String PAGE_RESET_PASSWORD = "security/resetPassword";
    private static final String PAGE_LOGIN = "security/login";
    private static final String PAGE_REGISTER = "security/register";
    private static final String PAGE_ACTIVATION_ERROR = "security/activationError";
    // redirects
    private static final String REDIRECT_FORGOTTEN_PASSWORD_SUCCESS = "redirect:forgottenPassword?success&email=";
    private static final String REDIRECT_REGISTER_SUCCESS = "redirect:register?success&email=";
    private static final String REDIRECT_LOGIN_SUCCESS = "redirect:login?email=";
    // links
    public static final String LINK_AUTH_CONTROLLER = "/auth";
    public static final String LINK_ACTIVATION = "/activation";
    public static final String LINK_LOGIN = "/login";
    public static final String LINK_RESET_PASSWORD = "/resetPassword";

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserService userService;

    @RequestMapping( value = LINK_LOGIN, method = RequestMethod.GET )
    public String login( Model model, @RequestParam( value = "email", required = false ) String email ) {
        LoginForm loginForm = new LoginForm();
        if(email!=null) {
            loginForm.setUsername(email);
        }
        model.addAttribute( "loginForm", loginForm );
        return PAGE_LOGIN;
    }

    @RequestMapping( value = LINK_LOGIN, params = "error" )
    public String errorLogin( Model model ) {
        return PAGE_LOGIN;
    }

    @RequestMapping( value = "/logout", method = RequestMethod.POST )
    public ModelAndView logout() {
        return new ModelAndView(PAGE_LOGIN);
    }

    @RequestMapping( value = "/register", method = RequestMethod.GET )
    public String registerUser( Model model, @RequestParam( value = "success", required = false ) String success, @RequestParam( value = "email", defaultValue = "", required = false ) String email ) {

        if ( success != null )
        {
            model.addAttribute( "messageText", messageSource.getMessage( "register.message.success", new Object[]{email}, LocaleContextHolder.getLocale() ) );
            return PAGE_BASIC_MESSAGE;
        }

        model.addAttribute( "registerForm", new RegisterForm() );
        return PAGE_REGISTER;
    }

    @RequestMapping( value = "/register", method = RequestMethod.POST )
    public ModelAndView registerUserPost( @Valid @ModelAttribute( "registerForm" ) RegisterForm registerForm, BindingResult result ) {
        RegisterFormValidator validator = new RegisterFormValidator();
        validator.validate( registerForm, result );
        if ( result.hasErrors() )
        {
            return new ModelAndView(PAGE_REGISTER);
        }

        try {
            User user = new User();
            user.setEmail( registerForm.getEmail() );
            user.setPlaintextPassword(registerForm.getPassword());
            userService.create( user );
            return new ModelAndView( REDIRECT_REGISTER_SUCCESS + registerForm.getEmail() );
        } catch (UniqueException e) {
            result.addError( new ObjectError( "email", "User with this email address already exist" ) );
        }
        return new ModelAndView(PAGE_REGISTER);
    }

    @RequestMapping( value = LINK_ACTIVATION, method = RequestMethod.GET )
    public ModelAndView activateAccount(
            Model model,
            @RequestParam( value = "email", required = true ) String email,
            @RequestParam( value = "key", required = true )
            String activationKey ) {
        try {
            userService.activateUser(email, activationKey);
            return new ModelAndView( REDIRECT_LOGIN_SUCCESS+email );
        } catch (BadAttemptException e)
        {
            return new ModelAndView(PAGE_ACTIVATION_ERROR);
        }
    }

    @RequestMapping( value = "/forgottenPassword", method = RequestMethod.GET )
    public String forgottenPassword( Model model, @RequestParam( value = "success", required = false ) String success, @RequestParam( value = "email", defaultValue = "", required = false ) String email ) {
        if(success!=null) {
            model.addAttribute("messageText", messageSource.getMessage("forgottenPassword.message.success", new Object[]{email}, LocaleContextHolder.getLocale()));
            return PAGE_BASIC_MESSAGE;
        }
        model.addAttribute( "formObject", new ForgottenPasswordForm() );
        return PAGE_FORGOTTEN_PASSWORD;
    }

    @RequestMapping( value = "/forgottenPassword", method = RequestMethod.POST )
    public ModelAndView forgottenPassword( @Valid @ModelAttribute( "formObject" ) ForgottenPasswordForm formObject, BindingResult result ) {
        if ( result.hasErrors() )
        {
            return new ModelAndView(PAGE_FORGOTTEN_PASSWORD);
        }
        if ( !userService.userExists( formObject.getEmail() ) )
        {
            result.addError( new ObjectError( "email", "#{forgottenPassword.error}" ) );
            return new ModelAndView(PAGE_FORGOTTEN_PASSWORD);
        }
        try {
            userService.sendForgottenPasswordMail(formObject.getEmail());
            return new ModelAndView(REDIRECT_FORGOTTEN_PASSWORD_SUCCESS+formObject.getEmail());
        } catch(BadAttemptException e) {
            result.addError( new ObjectError( "email", "#{forgottenPassword.error}" ) );
            return new ModelAndView(PAGE_FORGOTTEN_PASSWORD);
        }
    }

    @RequestMapping( value = LINK_RESET_PASSWORD, method = RequestMethod.GET, params = {"email", "key"} )
    public String resetPassword( Model model,
                                 @RequestParam( value = "email", required = true ) String email,
                                 @RequestParam( value = "key", required = true ) String activationKey
    ) {
        ResetPasswordForm formObject = new ResetPasswordForm();
        formObject.setEmail(email);
        formObject.setKey(activationKey);
        model.addAttribute( "formObject", formObject );
        return PAGE_RESET_PASSWORD;
    }

    @RequestMapping( value = LINK_RESET_PASSWORD, method = RequestMethod.POST )
    public ModelAndView resetPasswordPost(@Valid @ModelAttribute( "formObject" ) ResetPasswordForm formObject, BindingResult result) {
        ResetPasswordFormValidator validator = new ResetPasswordFormValidator();
        validator.validate( formObject, result );
        if ( result.hasErrors() )
        {
            return new ModelAndView(PAGE_RESET_PASSWORD);
        }
        try {
            userService.resetPassword(formObject.getEmail(), formObject.getPassword(), formObject.getKey());
        } catch (BadAttemptException e) {
            result.addError(new ObjectError("password", messageSource.getMessage(e.getMessage(),null,LocaleContextHolder.getLocale())));
        }
        return new ModelAndView(PAGE_RESET_PASSWORD);
    }

}

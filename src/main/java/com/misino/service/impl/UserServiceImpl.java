package com.misino.service.impl;

import com.misino.component.MailServiceImpl;
import com.misino.data.domain.Role;
import com.misino.data.domain.User;
import com.misino.data.enums.Language;
import com.misino.data.enums.UserState;
import com.misino.data.enums.UserType;
import com.misino.data.repositories.UserRepository;
import com.misino.security.MyPasswordEncoder;
import com.misino.service.UserService;
import com.misino.template.mail.AccountActivationMailTemplate;
import com.misino.template.mail.ForgottenPasswordMailTemplate;
import com.misino.utils.exceptions.BadAttemptException;
import com.misino.utils.exceptions.UniqueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService
{
    static final Logger log = LoggerFactory.getLogger( UserServiceImpl.class );

    @Resource
    private UserRepository userRepository;

    @Resource
    private Environment env;

    @Autowired
    MyPasswordEncoder encoder;

    @Autowired
    MailServiceImpl mailService;

    @Transactional
    public User create( User user ) throws UniqueException {

        if(userExists( user.getEmail() )) {
            throw new UniqueException( );
        }

        user.setState( UserState.NEW );

        user.setTimeRegistrated( (new Date()).getTime() );
        if(user.getType() == null) {
            user.setType( UserType.GUEST);
        }

        if(user.getLanguage() == null) {
            String lang = env.getProperty( "default.language" );
                    user.setLanguage( Language.valueOf( lang ) );
        }

        log.debug( "Creating new user: " + user );

        user.setPassword( encoder );
        user.generateActivationCode();
        User u = userRepository.save( user );

        mailService.addToQueue( new AccountActivationMailTemplate(user.getEmail(), u.getActivationCode(), LocaleContextHolder.getLocale()) );

        return u;
    }

    @Transactional
    public void delete( Long id ) {
        userRepository.delete( id );
    }

    @Transactional
    public User update( User user ) {
        User updatedUser = userRepository.findOne( user.getId() );
        if(updatedUser==null) {
        // TODO exception
        }
        //updatedUser.setEmail( user.getEmail() );

        if(user.getPlaintextPassword()!=null) {
            updatedUser.setPassword(user.getPlaintextPassword(), encoder);
        }

        return updatedUser;
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById( Long id ) {
        return userRepository.findOne( id );
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        User user = userRepository.findByEmail( username );
        if(user!=null) {
            List<Role> roles = new ArrayList<Role>();

            Role role = new Role();
            role.setName( user.getType().name().toString() );
            roles.add( role );
            //user.setAuthorities(roles);
        }
//        Role r = new Role();
//        r.setName("ROLE_USER");
//        List<Role> roles = new ArrayList<Role>();
//        roles.add(r);
//        //user.setAuthorities(roles);
        return user;
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail( email ) != null;
    }

    @Override
    public void activateUser(String email, String activationKey) throws BadAttemptException {
        User user = userRepository.findByEmail(email);
        if(!user.getState().equals(UserState.NEW) || !activationKey.equals(user.getActivationCode())) {
            throw new BadAttemptException();
        }
        user.setActivationCode(null);
        user.setState(UserState.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public void sendForgottenPasswordMail(String email) throws BadAttemptException {
        Integer timeTokenDurationHours = Integer.parseInt(env.getProperty("user.activation.code.durability.hours"));
        User user = userRepository.findByEmail(email);
        if(!user.getState().equals(UserState.ACTIVE)) {
            throw new BadAttemptException();
        }
        // if timetoken is set, do not perform any action
        if(user.getActivationCode() !=null && user.getTimeToken() != null && user.getTimeToken() > (new Date()).getTime()-3600*timeTokenDurationHours) {
            return;
        }
        user.generateActivationCode();
        user.setTimeToken();

        userRepository.save(user);

        mailService.addToQueue(
                new ForgottenPasswordMailTemplate(
                        email, user.getActivationCode(),
                        timeTokenDurationHours,
                        LocaleContextHolder.getLocale()
                ));
    }

    public void resetPassword(String email, String password, String key) throws BadAttemptException {
        Integer timeTokenDurationHours = Integer.parseInt(env.getProperty("user.activation.code.durability.hours"));
        User user = userRepository.findByEmail(email);

        if(user.getActivationCode()==null || user.getActivationCode().isEmpty() || !user.getActivationCode().equals(key)) {
            throw new BadAttemptException("exception.resetPassword.token.activationCodeNotValid", null);
        }
        if(user.getTimeToken()<(new Date()).getTime()-3600*timeTokenDurationHours) {
            throw new BadAttemptException("exception.resetPassword.token.expired",null);
        }
        user.setActivationCode(null);
        user.setTimeToken(null);
        user.setPassword(password, encoder);
        userRepository.save(user);

    }
}

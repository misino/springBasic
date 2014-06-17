package com.misino.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Created by misino on 12/25/13.
 */
@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider
{
//    @Autowired
//    UserService userService;

    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException {

        return super.authenticate( authentication );
//        String username = authentication.getName();
//        CharSequence password = (CharSequence)authentication.getCredentials();
//
//        UserDetails userDetails = this.getUserDetailsService()
//                .loadUserByUsername( username );
//
//        if (userDetails == null) {
//            throw new BadCredentialsException("Username not found.");
//        }
//
////        User user = (User) userDetails;
////        new SecureRandom( SecureRandom.getSeed( 32 ) );
////        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder( 20 );
////        String encodedPassword = passwordEncoder.encode( password +user.getSecretMagic());
//
//        if (!password.equals(userDetails.getPassword())) {
//            throw new BadCredentialsException("Wrong password.");
//        }
//        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
//        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        Collection<? extends GrantedAuthority> authorities = grantedAuths;//user.getAuthorities();
//
//        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports( Class<?> authentication ) {
        return true;
    }
}

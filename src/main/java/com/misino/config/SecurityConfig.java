package com.misino.config;

import com.misino.data.enums.UserType;
import com.misino.service.UserService;
import com.misino.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@ImportResource( "/authentication-manager.xml" )
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private javax.sql.DataSource dataSource;

//    @Autowired
//    CustomAuthenticationProvider authenticationProvider;

    // Services
    @Bean
    UserService userService() {
        return new UserServiceImpl();
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//
//        return super.authenticationManagerBean();
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers( "/admin/**" ).authenticated()
                .antMatchers( "/admin/**" ).hasRole( UserType.ADMIN.name() )
                .antMatchers("/auth/forgottenPassword").anonymous()
                .antMatchers("/auth/resetPassword").anonymous()
                .antMatchers("/**").permitAll()

                .anyRequest().permitAll();
                //.anyRequest().authenticated();
        http
            .formLogin()
                .loginPage("/auth/login")
                .usernameParameter( "username" )
                .passwordParameter( "password" )
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
            .logout()
                .logoutUrl( "/auth/logout" )
                .permitAll()
        ;

    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
//        //AuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
//        authManagerBuilder.authenticationProvider( this.authenticationProvider );
////        authManagerBuilder.jdbcAuthentication()
////                .dataSource( dataSource )
////                .usersByUsernameQuery( getUsernameQuery() )
////                .authoritiesByUsernameQuery( getAuthorityUsernameQuery() );
//    }

//    private String getUsernameQuery() {
//        return "SELECT email as username, password as password, state as enabled" +
//                "FROM user"+
//                "WHERE email = ?";
//    }
//
//    private String getAuthorityUsernameQuery() {
//        return "SELECT DISTINCT user.email as username, role.name as authority "
//                + "FROM user, user_role, role "
//                + "WHERE user.id = user_role.user_id "
//                + "AND role.id = user_role.role_id "
//                + "AND user.email = ? "
//                + "AND user.state < 1";
//    }

}

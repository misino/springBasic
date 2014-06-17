package com.misino.data.domain;


import com.misino.data.enums.Language;
import com.misino.data.enums.UserState;
import com.misino.data.enums.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Entity
@Table( name = "user" )
public class User implements UserDetails, Serializable
{

    private static final long serialVersionUID = -6464264698745512L;
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    @Column( name = "time_registrated" )
    private Long timeRegistrated;
    @Column( name = "time_lastlogin" )
    private Long timeLastLogin;
    @Column( name = "time_deleted" )
    private Long timeDeleted;
    @Enumerated(EnumType.STRING)
    private Language language;
    private UserState state;
    private UserType type;
    @Column( name = "secret_magic" )
    private String secretMagic;
    @Column( name = "activation_code" )
    private String activationCode;
    @Column( name = "time_token" )
    private Long timeToken;

    @Transient
    private String plaintextPassword;
    //private UserState state;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

//    public void setPassword( String password ) {
//        this.password = password;
//    }

    public void setPassword( PasswordEncoder encoder ) {
        this.setPassword(this.plaintextPassword, encoder );
    }

    public void setPassword( String password, PasswordEncoder encoder ) {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        /** String encodedSalt = Base64.encodeBase64String(salt); */

        this.password = encoder.encode( password );
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.state != UserState.DELETED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.state != UserState.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.state == UserState.ACTIVE;
    }

    public Long getTimeRegistrated() {
        return timeRegistrated;
    }

    public void setTimeRegistrated( Long timeRegistrated ) {
        this.timeRegistrated = timeRegistrated;
    }

    public Long getTimeLastLogin() {
        return timeLastLogin;
    }

    public void setTimeLastLogin( Long timeLastLogin ) {
        this.timeLastLogin = timeLastLogin;
    }

    public Long getTimeDeleted() {
        return timeDeleted;
    }

    public void setTimeDeleted( Long timeDeleted ) {
        this.timeDeleted = timeDeleted;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage( Language language ) {
        this.language = language;
    }

    public UserState getState() {
        return state;
    }

    public void setState( UserState state ) {
        this.state = state;
    }

    public UserType getType() {
        return type;
    }

    public void setType( UserType type ) {
        this.type = type;
    }

    public String getSecretMagic() {
        return secretMagic;
    }

    public void setSecretMagic( String secretMagic ) {
        this.secretMagic = secretMagic;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode( String activationCode ) {
        this.activationCode = activationCode;
    }

    public Long getTimeToken() {
        return timeToken;
    }

    public void setTimeToken() { this.timeToken = (new Date()).getTime(); }
    public void setTimeToken( Long timeToken ) {
        this.timeToken = timeToken;
    }

    public void setPlaintextPassword(String password) {
        this.plaintextPassword = password;
    }

    public String getPlaintextPassword() {
        return plaintextPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", timeRegistrated=" + timeRegistrated +
                ", timeLastLogin=" + timeLastLogin +
                ", timeDeleted=" + timeDeleted +
                ", language=" + language +
                ", state=" + state +
                ", type=" + type +
                ", secretMagic='" + secretMagic + '\'' +
                ", activationCode='" + activationCode + '\'' +
                ", timeToken=" + timeToken +
                '}';
    }

    /**
     * It is used in scenarios when user model contains not encoded password.
     * @param encoder
     */
    public void encodePassword(PasswordEncoder encoder) {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        /** String encodedSalt = Base64.encodeBase64String(salt); */

        this.password = encoder.encode( password );
    }

    public void generateActivationCode() {
        UUID uuid = UUID.randomUUID();
        this.setActivationCode(uuid.toString());
    }
}

package com.misino.data.domain;

import com.misino.data.enums.UserType;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Created by misino on 12/25/13.
 */
public class Role implements GrantedAuthority
{
    private String name;

    private List<UserType> privileges;

    @Override
    public String getAuthority() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<UserType> getPrivileges() {
        return privileges;
    }

    public void setPrivileges( List<UserType> privileges ) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Role [name=");
        builder.append(name);
        builder.append(", privileges=");
        builder.append(privileges);
        builder.append("]");
        return builder.toString();
    }
}

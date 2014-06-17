package com.misino.data.enums;


public enum UserState
{
    /**
     * if you want to add other active state, reconfigure {@link com.misino.config.SecurityConfig#}
     *
     */
    NEW(0),
    ACTIVE(1),
    DELETED(2),
    BLOCKED(3);

    UserState(Integer state) {
    }

}

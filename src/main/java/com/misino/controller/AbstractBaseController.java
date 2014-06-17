package com.misino.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
abstract public class AbstractBaseController
{
    public Object getUser() {
        return getAuthentication().getPrincipal();
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

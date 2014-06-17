package com.misino.config.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class GlobalModelAttributesInterceptor implements WebRequestInterceptor
{

    @Override
    public void preHandle( WebRequest request ) throws Exception {

    }

    @Override
    public void postHandle( WebRequest request, ModelMap model ) throws Exception {
        model.addAttribute( "pageTitle", "Misino" );
    }

    @Override
    public void afterCompletion( WebRequest request, Exception ex ) throws Exception {

    }
}

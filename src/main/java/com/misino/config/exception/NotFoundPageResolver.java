package com.misino.config.exception;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundPageResolver extends ExceptionHandlerExceptionResolver//HandlerExceptionResolver
{
    @Override
    public ModelAndView resolveException( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) {

        return new ModelAndView( "error/other" );
    }
}

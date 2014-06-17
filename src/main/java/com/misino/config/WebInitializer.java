package com.misino.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Order(1)
//@ImportResource( "/WEB-INF/web.xml" )
@PropertySource(name="global", value="classpath:global.properties")
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    public String pageTitle;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { ServiceConfig.class, MysqlDatabaseConfig.class, SecurityConfig.class, TemplatesConfig.class, MailConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    public void onStartup( ServletContext servletContext ) throws ServletException {
        super.onStartup( servletContext );
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding( "UTF-8" );
       // encodingFilter.setForceEncoding( true );
        return new Filter[]{encodingFilter, new DelegatingFilterProxy("springSecurityFilterChain")};
    }
}

package com.misino.config;

import com.misino.template.bootstrap.BootstrapDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.Locale;

@Configuration
public class TemplatesConfig
{
    @Bean
    public ClassLoaderTemplateResolver mailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix( "mail/" );
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding( "UTF-8" );
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public ServletContextTemplateResolver webTemplateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix( "/WEB-INF/views/" );
        resolver.setSuffix( ".html" );
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(2);
        //resolver.setCharacterEncoding( "UTF-8" );

        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(this.webTemplateResolver());
        templateEngine.addTemplateResolver(this.mailTemplateResolver());
        templateEngine.addDialect( new SpringSecurityDialect() );
        templateEngine.addDialect( new LayoutDialect() );
        templateEngine.addDialect( new BootstrapDialect() );
        return templateEngine;
    }

    @Bean
    ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(this.templateEngine());
        thymeleafViewResolver.setCache(false);
        thymeleafViewResolver.setContentType("text/html; charset=UTF-8");
        thymeleafViewResolver.setCacheLimit(1);
        return thymeleafViewResolver;
    }



    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        Locale locale = new Locale( "en" );
        localeResolver.setDefaultLocale( locale );
        return localeResolver;
    }

    @Bean
    ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        //source.setBasename( "/WEB-INF/messages/messages" );
        source.setBasenames( "classpath:i18n/messages", "classpath:i18n/ValidationMessages", "classpath:i18n/email" );
        source.setDefaultEncoding( "UTF-8" );
        source.setUseCodeAsDefaultMessage( false );
        source.setCacheSeconds( 1 );
        return source;
    }

//    public RequestMappingHandlerMapping requestHandling() {
//        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
//        requestMappingHandlerMapping.setDefaultHandler( localeChangeInterceptor() );
//        return requestMappingHandlerMapping;
//    }
}

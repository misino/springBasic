package com.misino.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= "com.misino.controller" )
public class WebConfig extends WebMvcConfigurerAdapter
{


    /**
     * <mvc:resources mapping="/static/**" location="classpath:/WEB-INF/static/"/>
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/")
                .setCachePeriod(31556926);
    }

    /**
     * <mvc:default-servlet-handler/>
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login").setViewName("security/login");
        //registry.setOrder( Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        resolvers.add(new NotFoundPageResolver());
        resolvers.add( new ResponseStatusExceptionResolver() );
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName( "lang" );
        return localeChangeInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor( new WebRequestHandlerInterceptorAdapter( new GlobalModelAttributesInterceptor() ) );
        registry.addInterceptor(localeChangeInterceptor());
    }


}

package com.example.chatroom2.interceptor;

import javafx.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

    @Value("${web.upload-path}")
    private String path;

    private ApplicationContext applicationContext;

    public WebConfig(){
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }




    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/login2")
                .excludePathPatterns("/chatroom")
                .excludePathPatterns("/register")
                .excludePathPatterns("/error")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/show/**")
                .excludePathPatterns("/css/**", "/js/**", "/img/**", "/fontawesome-free-5.11.2-web/**","/favicon.ico");


    }
//
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
        //?????????/static/** ??????????????????classpath:/static/ ?????????
        //addResourceLocations????????????????????????'/'??????,?????????????????????,????????????????????????????????????????????????(??????: classpath:/xxx/xx/, file:/xxx/xx/, http://xxx/xx/)
      registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
//        registry.addResourceHandler("/img/**").addResourceLocations("file:/"+path);
    }
}

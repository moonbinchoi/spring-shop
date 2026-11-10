package com.study.shop;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.study.shop.web.argumentresolver.LoginMemberArgumentResolver;
import com.study.shop.web.filter.LoginCheckFilter;
import com.study.shop.web.interceptor.LogInterceptor;
import com.study.shop.web.interceptor.LoginCheckInterceptor;

import jakarta.servlet.Filter;

@Configuration 
public class WebConfig implements WebMvcConfigurer {

    //@Bean 
    public FilterRegistrationBean<Filter> loginCheckFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LoginCheckFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");

        return bean;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
        
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}

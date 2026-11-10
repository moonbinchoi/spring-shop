package com.study.shop.web.interceptor;

import java.util.UUID;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class LogInterceptor implements HandlerInterceptor {
    public static final String LOG_ID = "logId";

    @Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {
        UUID uuid = UUID.randomUUID();
        request.setAttribute(LOG_ID, uuid);
        log.info("REQUEST [{}][{}][{}]", uuid, request.getRequestURI(), handler);

        return true;
    }
    
    @Override 
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override 
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) {
        log.info("RESPONSE [{}][{}]", request.getAttribute(LOG_ID), request.getRequestURI());
        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}

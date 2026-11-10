package com.study.shop.web.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import com.study.shop.web.SessionConst;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {
    private static final String[] allow = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!PatternMatchUtils.simpleMatch(allow, req.getRequestURI())) {
            HttpSession session = req.getSession(false);
            
            if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                res.sendRedirect("/login?redirectURL=" + req.getRequestURI());
                return;
            }
        }

        chain.doFilter(request, response);
    }
}

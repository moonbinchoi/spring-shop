package com.study.shop.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.study.shop.domain.login.LoginService;
import com.study.shop.domain.member.Member;
import com.study.shop.web.SessionConst;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;



@Controller 
@RequiredArgsConstructor 
public class LoginController {
    
    private final LoginService service;
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }
    
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
                            
        if (bindingResult.hasErrors())
            return "login/loginForm";
            
        Member login = service.login(form.getLoginId(), form.getPassword());
        if (login == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, login);
        return "redirect:/";
    }
    
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        
        if (session != null)
            session.invalidate();
            
        return "redirect:/";
    }
    
}

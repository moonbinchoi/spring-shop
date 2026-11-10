package com.study.shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.shop.domain.member.Member;
import com.study.shop.web.argumentresolver.Login;



@Controller 
public class HomeController {
    
    @GetMapping("/")
    public String home(@Login Member member,
                       Model model) {
        if (member == null) 
            return "home";
        
        model.addAttribute("member", member);
        return "loginHome";
    }
    
}

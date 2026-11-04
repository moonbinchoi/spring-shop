package com.study.shop.web.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.shop.domain.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Controller 
@RequiredArgsConstructor 
@RequestMapping("/members")
public class MemberController {
    
    private final MemberRepository memberRepository;
    
    @GetMapping("/add")
    public String addMember(Model model) {
        model.addAttribute("member", new MemberSaveForm());
        return "members/addMemberForm";
    }
    
    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") MemberSaveForm form,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "members/addMemberForm";
        
        memberRepository.save(form.toMember());
        return "redirect:/";
    }
}

package com.study.shop.web.member;

import com.study.shop.domain.member.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class MemberSaveForm {
    
    @NotBlank 
    private String loginId;

    @NotBlank 
    private String password;

    @NotBlank 
    private String name;
    
    public Member toMember() {
        Member member = new Member();
        
        member.setLoginId(loginId);
        member.setName(name);
        member.setPassword(password);
        
        return member;
    }
}

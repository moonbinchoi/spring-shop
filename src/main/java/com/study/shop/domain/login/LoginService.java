package com.study.shop.domain.login;


import org.springframework.stereotype.Service;

import com.study.shop.domain.member.Member;
import com.study.shop.domain.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class LoginService {

    private final MemberRepository repository;

    public Member login(String loginId, String password) {
        return repository.findByLoginId(loginId)
                    .filter(m -> m.getPassword().equals(password))
                    .orElse(null);
    }
}

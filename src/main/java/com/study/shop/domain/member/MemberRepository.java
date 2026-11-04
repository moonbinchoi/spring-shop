package com.study.shop.domain.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository 
public class MemberRepository {
    
    private final Map<Long, Member> repository = new HashMap<Long, Member>();

    private static long memberId;

    public Member save(Member member) {
        member.setId(memberId);
        repository.put(memberId, member);
        memberId++;

        return member;
    }

    public Member findById(Long id) {
        return repository.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return repository.values().stream()
                    .filter(b -> b.getLoginId().equals(loginId))
                    .findFirst();
    }

    public List<Member> findAll() {
        return List.copyOf(repository.values());
    }
}

package com.study.shop;

import org.springframework.stereotype.Component;

import com.study.shop.domain.item.Item;
import com.study.shop.domain.item.ItemRepository;
import com.study.shop.domain.member.Member;
import com.study.shop.domain.member.MemberRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class TestDataInit {
    
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    
    @PostConstruct 
    void test() {
        Item itemA = new Item();
        Item itemB = new Item();
        Member member = new Member();
        
        itemA.setItemName("itemA");
        itemA.setPrice(10000);
        itemA.setQuantity(10);
        
        itemB.setItemName("itemB");
        itemB.setPrice(20000);
        itemB.setQuantity(20);
        
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스터");
        
        itemRepository.save(itemA);
        itemRepository.save(itemB);
        memberRepository.save(member);
    }
}

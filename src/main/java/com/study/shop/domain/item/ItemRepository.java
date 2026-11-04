package com.study.shop.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository 
public class ItemRepository {
    
    
    private final Map<Long, Item> repository = new HashMap<Long, Item>();

    private static long id = 0;

    public Item save(Item item) {
        item.setId(id);
        repository.put(id++, item);
        return item;
    }

    public Item findById(Long id) {
        return repository.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(repository.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item i = findById(itemId);

        i.setItemName(updateParam.getItemName());
        i.setPrice(updateParam.getPrice());
        i.setQuantity(updateParam.getQuantity());

        repository.put(itemId, i);
    }
}

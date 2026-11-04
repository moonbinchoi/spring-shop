package com.study.shop.web.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.shop.domain.item.Item;
import com.study.shop.domain.item.ItemRepository;
import com.study.shop.web.item.form.ItemSaveForm;
import com.study.shop.web.item.form.ItemUpdateForm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller 
@RequestMapping("/items")
@RequiredArgsConstructor 
public class ItemController {
    
    private final ItemRepository itemRepository;

    @GetMapping("/{itemId}")
    public String getItem(@PathVariable Long itemId, Model model) {
        model.addAttribute("item", itemRepository.findById(itemId));
        return "items/item";
    }
    

    @GetMapping
    public String getItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "items/items";
    }

    @GetMapping("/add")
    public String addItem(Model model) {
        model.addAttribute("item", new Item());
        return "items/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, 
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "items/addForm";
        }

        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }
    
    @GetMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, Model model) {
        model.addAttribute(itemRepository.findById(itemId));
        return "items/editForm";
    }
    
    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, 
                            @Validated @ModelAttribute("item") ItemUpdateForm form,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/editForm";
        }

        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());
        itemRepository.update(itemId, item);

        return "redirect:/items/{itemId}";
    }
    
}

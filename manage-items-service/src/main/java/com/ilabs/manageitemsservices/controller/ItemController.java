package com.ilabs.manageitemsservices.controller;

import com.ilabs.manageitemsservices.dto.ItemRequestDto;
import com.ilabs.manageitemsservices.dto.ItemResponseDto;
import com.ilabs.manageitemsservices.dto.SuccessResponseDto;
import com.ilabs.manageitemsservices.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/item")
    public ResponseEntity<ItemResponseDto> addItem(@RequestBody ItemRequestDto itemRequest) {
        return itemService.addItem(itemRequest);
    }

    @PatchMapping("/item/{itemCode}")
    public ResponseEntity<ItemResponseDto> updateItem(@RequestBody ItemRequestDto itemRequest, @PathVariable("itemCode") Long itemCode) {
        return itemService.updateItem(itemRequest, itemCode);
    }

    @DeleteMapping("/item/{itemCode}")
    public ResponseEntity<SuccessResponseDto> removeItem(@PathVariable("itemCode") Long itemCode) {
        return itemService.removeItem(itemCode);
    }

    @GetMapping("/item/{itemCode}")
    public ResponseEntity<ItemResponseDto> fetchItem(@PathVariable("itemCode") Long itemCode) {
        return itemService.fetchItem(itemCode);
    }
}

package com.ilabs.manageitemsservices.service;

import com.ilabs.manageitemsservices.dto.ItemRequestDto;
import com.ilabs.manageitemsservices.dto.ItemResponseDto;
import com.ilabs.manageitemsservices.dto.SuccessResponseDto;
import org.springframework.http.ResponseEntity;

public interface ItemService {
    ResponseEntity<ItemResponseDto> addItem(ItemRequestDto itemRequest);

    ResponseEntity<ItemResponseDto> updateItem(ItemRequestDto itemRequest, Long itemCode);

    ResponseEntity<SuccessResponseDto> removeItem(Long itemCode);

    ResponseEntity<ItemResponseDto> fetchItem(Long itemCode);
}

package com.ilabs.cartservice.service;

import com.ilabs.cartservice.dto.CartRequestDto;
import com.ilabs.cartservice.dto.CartResponseDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<CartResponseDto> addItemsToCart(CartRequestDto cartRequest);

    ResponseEntity<CartResponseDto> removeItemsFromCart(CartRequestDto cartRequest);
}

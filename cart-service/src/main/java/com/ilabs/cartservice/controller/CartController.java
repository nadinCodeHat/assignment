package com.ilabs.cartservice.controller;

import com.ilabs.cartservice.dto.CartRequestDto;
import com.ilabs.cartservice.dto.CartResponseDto;
import com.ilabs.cartservice.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /*
    @Todo add swagger
     */
    //Add items to the cart
    @PostMapping("/cart")
    public ResponseEntity<CartResponseDto> addItemsToCart(@RequestBody CartRequestDto cartRequest) {
        return cartService.addItemsToCart(cartRequest);
    }

    //Remove items from cart
    @DeleteMapping("/cart")
    public ResponseEntity<CartResponseDto> removeItemsFromCart(@RequestBody CartRequestDto cartRequest) {
        return cartService.removeItemsFromCart(cartRequest);
    }
}

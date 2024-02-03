package com.ilabs.cartservice.dto;

import com.ilabs.cartservice.entity.Cart;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CartRequestDto {

    private Integer userId;

    private Long itemCode;

    private Integer quantity;

    public Cart toEntity() {
        Cart cart = new Cart();
        BeanUtils.copyProperties(this, cart);
        return cart;
    }
}

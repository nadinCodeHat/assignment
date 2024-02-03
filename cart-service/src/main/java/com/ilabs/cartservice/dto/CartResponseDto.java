package com.ilabs.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private Long cartId;

    private Integer userId;

    private Long itemCode;

    private int quantity;

    private BigDecimal totalPrice;
}

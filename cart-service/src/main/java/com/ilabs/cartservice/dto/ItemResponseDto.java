package com.ilabs.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {

    private Long itemCode;

    private String itemName;

    private String itemDescription;

    private BigDecimal itemPrice;

    private Integer quantity;
}

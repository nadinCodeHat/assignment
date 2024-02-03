package com.ilabs.cartservice.entity;

import com.ilabs.cartservice.dto.CartResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "item_code")
    private Long itemCode;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public CartResponseDto toDto() {
        CartResponseDto dto = new CartResponseDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}

package com.ilabs.manageitemsservices.entity;

import com.ilabs.manageitemsservices.dto.ItemResponseDto;
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
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_code")
    private Long itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    private Integer quantity;

    public ItemResponseDto toDto() {
        ItemResponseDto dto = new ItemResponseDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}

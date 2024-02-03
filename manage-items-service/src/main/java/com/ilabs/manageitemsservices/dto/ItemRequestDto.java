package com.ilabs.manageitemsservices.dto;

import com.ilabs.manageitemsservices.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    private String itemName;

    private String itemDescription;

    private BigDecimal itemPrice;

    private Integer quantity;

    public Item toEntity() {
        Item item = new Item();
        BeanUtils.copyProperties(this, item);
        return item;
    }
}

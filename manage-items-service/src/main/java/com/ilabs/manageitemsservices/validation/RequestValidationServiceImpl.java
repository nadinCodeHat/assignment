package com.ilabs.manageitemsservices.validation;

import com.ilabs.manageitemsservices.dto.ItemRequestDto;
import com.ilabs.manageitemsservices.exception.InvalidRequestDataException;
import org.springframework.stereotype.Service;

@Service
public class RequestValidationServiceImpl implements RequestValidationService {
    @Override
    public void validateAddItemRequest(ItemRequestDto dto) {
        if (dto == null) {
            throw new InvalidRequestDataException("Enter item creation information");
        }
        this.validateAddItemData(dto);
    }

    private void validateAddItemData(ItemRequestDto dto) {

        //VALIDATE ITEM NAME
        if (dto.getItemName() == null
                || dto.getItemName().isEmpty()) {
            throw new InvalidRequestDataException("Item name is missing");
        }

        //VALIDATE ITEM DESCRIPTION
        if (dto.getItemDescription() == null
                || dto.getItemDescription().isEmpty()) {
            throw new InvalidRequestDataException("Item description is missing");
        }

        //VALIDATE ITEM PRICE
        if (dto.getItemPrice() == null) {
            throw new InvalidRequestDataException("Item price is missing");
        }

        //VALIDATE ITEM QUANTITY
        if (dto.getQuantity() == null
                || dto.getItemDescription().isEmpty()) {
            throw new InvalidRequestDataException("Quantity is missing");
        }
    }
}

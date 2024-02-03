package com.ilabs.cartservice.validation;

import com.ilabs.cartservice.dto.CartRequestDto;
import com.ilabs.cartservice.exception.InvalidRequestDataException;
import org.springframework.stereotype.Service;

@Service
public class RequestValidationServiceImpl implements RequestValidationService {
    @Override
    public void validateCartItemRequest(CartRequestDto dto) {
        if (dto == null) {
            throw new InvalidRequestDataException("Enter item to cart information");
        }
        this.validateCartData(dto);
    }

    private void validateCartData(CartRequestDto dto) {

        //VALIDATE ITEM CODE
        if (dto.getItemCode() == null) {
            throw new InvalidRequestDataException("Item code is missing");
        }

        //VALIDATE USER ID
        if (dto.getUserId() == null) {
            throw new InvalidRequestDataException("User id is missing");
        }

        //VALIDATE ITEM QUANTITY
        if (dto.getQuantity() == null) {
            throw new InvalidRequestDataException("Item quantity is missing");
        }

        //VALIDATE ITEM QUANTITY
        if (dto.getQuantity() == 0) {
            throw new InvalidRequestDataException("Item quantity cannot be 0");
        }
    }
}

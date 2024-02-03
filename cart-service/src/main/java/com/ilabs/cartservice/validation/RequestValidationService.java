package com.ilabs.cartservice.validation;

import com.ilabs.cartservice.dto.CartRequestDto;

public interface RequestValidationService {
    void validateCartItemRequest(CartRequestDto cartRequest);
}

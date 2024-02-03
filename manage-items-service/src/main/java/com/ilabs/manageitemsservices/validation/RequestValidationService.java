package com.ilabs.manageitemsservices.validation;

import com.ilabs.manageitemsservices.dto.ItemRequestDto;

public interface RequestValidationService {
    void validateAddItemRequest(ItemRequestDto itemRequest);
}

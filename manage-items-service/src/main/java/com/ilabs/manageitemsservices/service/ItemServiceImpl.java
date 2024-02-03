package com.ilabs.manageitemsservices.service;

import com.ilabs.manageitemsservices.dto.ItemRequestDto;
import com.ilabs.manageitemsservices.dto.ItemResponseDto;
import com.ilabs.manageitemsservices.dto.SuccessResponseDto;
import com.ilabs.manageitemsservices.entity.Item;
import com.ilabs.manageitemsservices.repository.ItemRepository;
import com.ilabs.manageitemsservices.validation.RequestValidationService;
import com.ilabs.manageitemsservices.exception.InvalidBadRequestException;
import com.ilabs.manageitemsservices.exception.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.core.support.MethodLookup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(MethodLookup.class);

    private final ItemRepository itemRepository;
    private final RequestValidationService requestValidationService;

    public ItemServiceImpl(ItemRepository itemRepository, RequestValidationService requestValidationService) {
        this.itemRepository = itemRepository;
        this.requestValidationService = requestValidationService;
    }

    @Override
    public ResponseEntity<ItemResponseDto> addItem(ItemRequestDto itemRequest) {
        try {
            logger.info("ADD ITEM METHOD INVOKED ITEM REQUEST PAYLOAD {}", itemRequest);

            this.requestValidationService.validateAddItemRequest(itemRequest);

            Item item = itemRequest.toEntity();

            ItemResponseDto itemResponse = itemRepository.save(item).toDto();

            logger.info("ADD ITEM METHOD FINISHED ITEM RESPONSE PAYLOAD {}", itemResponse);

            return new ResponseEntity<>(Objects.requireNonNull(itemResponse), HttpStatus.OK);

        } catch (HttpClientErrorException.BadRequest ex) {
            throw new InvalidBadRequestException(ex.getStatusText(), ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ItemResponseDto> updateItem(ItemRequestDto itemRequest, Long itemCode) {
        try {
            logger.info("UPDATE ITEM METHOD INVOKED ITEM REQUEST PAYLOAD {} : itemCode {}", itemRequest, itemCode);

            this.requestValidationService.validateAddItemRequest(itemRequest);

            Optional<Item> item = itemRepository.findById(itemCode);

            if (item.isPresent()) {
                Item updatedItem = item.get().builder()
                        .itemName(itemRequest.getItemName())
                        .itemDescription(itemRequest.getItemDescription())
                        .itemPrice(itemRequest.getItemPrice())
                        .quantity(itemRequest.getQuantity()).build();

                ItemResponseDto itemResponse = itemRepository.save(updatedItem).toDto();

                logger.info("UPDATE ITEM METHOD FINISHED ITEM RESPONSE PAYLOAD {}", itemResponse);

                return new ResponseEntity<>(Objects.requireNonNull(itemResponse), HttpStatus.OK);
            }

            return null;

        } catch (HttpClientErrorException.BadRequest ex) {
            throw new InvalidBadRequestException(ex.getStatusText(), ex.getMessage());
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ItemNotFoundException(ex.getStatusText(), String.format("Item not found with itemCode %s", itemCode));
        }
    }

    @Override
    public ResponseEntity<SuccessResponseDto> removeItem(Long itemCode) {
        try {
            logger.info("REMOVE ITEM METHOD INVOKED ITEM itemCode {}", itemCode);

            Optional<Item> item = itemRepository.findById(itemCode);

            if (item.isPresent()) {

                itemRepository.deleteById(itemCode);

                logger.info("REMOVE ITEM METHOD FINISHED");

                return ResponseEntity.status(HttpStatus.OK).body(
                        new SuccessResponseDto("RESPONSE SUCCESS", "Item removed successfully"));
            }

            return null;

        } catch (HttpClientErrorException.BadRequest ex) {
            throw new InvalidBadRequestException(ex.getStatusText(), ex.getMessage());
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ItemNotFoundException(ex.getStatusText(), String.format("Item not found with itemCode %s", itemCode));
        }
    }

    @Override
    public ResponseEntity<ItemResponseDto> fetchItem(Long itemCode) {
        try {
            logger.info("FETCH ITEM METHOD INVOKED ITEM itemCode {}", itemCode);

            Optional<Item> item = itemRepository.findById(itemCode);

            if (item.isPresent()) {

                ItemResponseDto itemResponse = item.get().toDto();

                logger.info("REMOVE ITEM METHOD FINISHED");

                return new ResponseEntity<>(Objects.requireNonNull(itemResponse), HttpStatus.OK);
            }

            return null;

        } catch (HttpClientErrorException.BadRequest ex) {
            throw new InvalidBadRequestException(ex.getStatusText(), ex.getMessage());
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ItemNotFoundException(ex.getStatusText(), String.format("Item not found with itemCode %s", itemCode));
        }
    }
}

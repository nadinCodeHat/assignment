package com.ilabs.cartservice.service;

import com.ilabs.cartservice.dto.CartRequestDto;
import com.ilabs.cartservice.dto.CartResponseDto;
import com.ilabs.cartservice.dto.ItemResponseDto;
import com.ilabs.cartservice.entity.Cart;
import com.ilabs.cartservice.exception.InvalidBadRequestException;
import com.ilabs.cartservice.exception.InvalidRequestDataException;
import com.ilabs.cartservice.repository.CartRepository;
import com.ilabs.cartservice.validation.RequestValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.core.support.MethodLookup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = LoggerFactory.getLogger(MethodLookup.class);
    private final CartRepository cartRepository;
    private final RequestValidationService requestValidationService;
    private final RestTemplate restTemplate;

    public CartServiceImpl(CartRepository cartRepository, RequestValidationService requestValidationService, RestTemplate restTemplate) {
        this.cartRepository = cartRepository;
        this.requestValidationService = requestValidationService;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<CartResponseDto> addItemsToCart(CartRequestDto cartRequest) {
        try {
            logger.info("ADD ITEMS TO CART METHOD INVOKED ITEM REQUEST PAYLOAD {}", cartRequest);

            this.requestValidationService.validateCartItemRequest(cartRequest);

            ResponseEntity<ItemResponseDto> itemInStock = restTemplate.getForEntity("http://MANAGE-ITEMS-SERVICE/api/v1/item/{itemCode}",
                    ItemResponseDto.class, cartRequest.getItemCode());

            Optional<Cart> cartItem = cartRepository.findCartByItemCodeAndUserId(cartRequest.getItemCode(), cartRequest.getUserId());

            CartResponseDto cartResponse;

            if (cartItem.isEmpty()) {
                if (Objects.requireNonNull(itemInStock.getBody()).getQuantity() < cartRequest.getQuantity())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock!");

                Cart cart = Cart.builder()
                        .userId(cartRequest.getUserId())
                        .itemCode(cartRequest.getItemCode())
                        .quantity(cartRequest.getQuantity())
                        .totalPrice(itemInStock.getBody().getItemPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())))
                        .build();

                cartResponse = cartRepository.save(cart).toDto();

            } else {
                Cart cartItemEntity = cartItem.get();
                if (Objects.requireNonNull(itemInStock.getBody()).getQuantity() < (cartRequest.getQuantity() + cartItemEntity.getQuantity()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock!");

                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + cartRequest.getQuantity());
                cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice().add(itemInStock.getBody().getItemPrice()
                        .multiply(BigDecimal.valueOf(cartRequest.getQuantity()))));
                cartResponse = cartRepository.save(cartItemEntity).toDto();
            }

            logger.info("ADD ITEMS TO CART METHOD FINISHED ITEM RESPONSE PAYLOAD {}", cartResponse);

            return new ResponseEntity<>(Objects.requireNonNull(cartResponse), HttpStatus.OK);

        } catch (HttpClientErrorException.BadRequest ex) {
            throw new InvalidBadRequestException(ex.getStatusText(), ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<CartResponseDto> removeItemsFromCart(CartRequestDto cartRequest) {
        try {
            logger.info("REMOVE ITEMS FROM CART METHOD INVOKED ITEM REQUEST PAYLOAD {}", cartRequest);

            this.requestValidationService.validateCartItemRequest(cartRequest);

            ResponseEntity<ItemResponseDto> itemInStock = restTemplate.getForEntity("http://MANAGE-ITEMS-SERVICE/api/v1/item/{itemCode}",
                    ItemResponseDto.class, cartRequest.getItemCode());

            Optional<Cart> cartItem = cartRepository.findCartByItemCodeAndUserId(cartRequest.getItemCode(), cartRequest.getUserId());

            CartResponseDto cartResponse;

            if (cartItem.isEmpty()) {
                throw new InvalidRequestDataException(String.format("No item found with userId %s : itemCode %s",
                        cartRequest.getUserId(), cartRequest.getItemCode()));
            } else {
                Cart cartItemEntity = cartItem.get();
                if (Objects.requireNonNull(cartItemEntity.getQuantity()) < cartRequest.getQuantity())
                    throw new InvalidRequestDataException(String.format("Invalid item quantity %s", cartRequest.getQuantity()));

                cartItemEntity.setQuantity(cartRequest.getQuantity());
                cartItemEntity.setTotalPrice(itemInStock.getBody().getItemPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())));
                cartResponse = cartRepository.save(cartItemEntity).toDto();
            }

            logger.info("REMOVE ITEMS TO CART METHOD FINISHED ITEM RESPONSE PAYLOAD {}", cartResponse);

            return new ResponseEntity<>(Objects.requireNonNull(cartResponse), HttpStatus.OK);

        } catch (HttpClientErrorException.BadRequest ex) {
            throw new InvalidBadRequestException(ex.getStatusText(), ex.getMessage());
        }
    }
}

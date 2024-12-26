package com.customer.cart.controller;

import com.customer.cart.model.Cart;
import com.customer.cart.service.CartServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@Slf4j
public class CartController {

    private final CartServiceInterface cartServiceInterface;

    @PostMapping("/cart/{productId}")
    public Cart addItemsToCart(@PathVariable Long productId, @RequestParam Integer quantity) {
        log.info("Entering into adding cart items API ");
        return cartServiceInterface.addItemsToCart(productId, quantity);
    }

    @GetMapping("/cart/{cartId}")
    public Cart getCartByCartId(@PathVariable Long cartId) {
        log.info("Entering into get cart by Id API ");
        return cartServiceInterface.getCartItemsById(cartId);
    }
}

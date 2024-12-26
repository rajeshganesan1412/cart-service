package com.customer.cart.service;


import com.customer.cart.model.Cart;

public interface CartServiceInterface {

    Cart addItemsToCart(Long id, Integer Quantity);


    Cart getCartItemsById(Long id);
}
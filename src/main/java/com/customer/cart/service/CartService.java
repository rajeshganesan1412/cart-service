package com.customer.cart.service;

import com.customer.cart.client.ProductServiceClient;
import com.customer.cart.exception.ProductNotFoundException;
import com.customer.cart.model.Cart;
import com.customer.cart.model.CartProduct;
import com.customer.cart.model.Product;
import com.customer.cart.repository.CartProductRepository;
import com.customer.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class CartService implements CartServiceInterface{

    private final CartRepository cartRepository;

    private final ProductServiceClient productServiceClient;

    private final CartProductRepository cartProductRepository;

    @Override
    @Transactional
    public Cart addItemsToCart(Long id, Integer quantity) {
        log.info("Adding items into cart");
        Product product = productServiceClient.getProductFromProductService(id);
        List<CartProduct> cartProductList = cartProductRepository.saveAll(getCartProducts(quantity, product));
        Cart cart = Cart.builder()
                .cartProducts(cartProductList)
                .build();
        return cartRepository.save(cart);
    }

    private List<CartProduct> getCartProducts(Integer quantity, Product product) {
        log.info("Getting cart products list");
        List<CartProduct> cartProductList = new ArrayList<>();
        CartProduct cartProduct = CartProduct.builder()
                .cartProductId(generateCartProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .category(product.getCategory())
                .quantity(quantity)
                .price(product.getPrice())
                .build();
        cartProductList.add(cartProduct);
        return cartProductList;
    }


    @Override
    public Cart getCartItemsById(Long id) {
        log.info("Getting cart items by id");
        return Optional.of(cartRepository.findById(id)).get()
                .orElseThrow(() -> new ProductNotFoundException("No Cart available in this cart id", HttpStatus.NOT_FOUND));
    }

    public Long generateCartProductId() {
        log.info("Generate random cart id");
        Random random = new Random();
        // Generate a random number between 1,000,000 and 9,999,999
        int min = 1000000;
        int max = 9999999;
        int randomNumber = random.nextInt(max - min + 1) + min;
        return (long) randomNumber;
    }
}

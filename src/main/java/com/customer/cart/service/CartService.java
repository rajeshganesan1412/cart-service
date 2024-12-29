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
    public Cart addItemsToCart(Long id, Integer quantity, String userId) {
         Cart cartForUser = cartRepository.findByUserId(userId);
        Product product = productServiceClient.getProductFromProductService(id);
         if (cartForUser == null) {
             List<CartProduct> newCartProductList = new ArrayList<>();
             log.info("Adding items into cart");
             CartProduct cartProduct = cartProductRepository.save(buildCartProduct(quantity, product));
             newCartProductList.add(cartProduct);
             Cart cart = Cart.builder()
                     .userId(userId)
                     .cartProducts(newCartProductList)
                     .build();
             return cartRepository.save(cart);
         } else {
             Optional<CartProduct> optionalCartProduct = cartForUser.getCartProducts().stream()
                     .filter(cartProduct-> cartProduct.getCartProductId().equals(product.getId()))
                     .findFirst();
             CartProduct cartProduct;
             if (optionalCartProduct.isPresent()) {
                 cartProductRepository.save(buildCartProductFromAlreadyAvailableCartProduct(quantity, optionalCartProduct.get()));
             } else {
                 cartProduct = cartProductRepository.save(buildCartProduct(quantity, product));
                 cartForUser.getCartProducts().add(cartProduct);
             }
             return cartRepository.save(cartForUser);
         }
    }

    public void deleteCartByCartId(Long cartId) {
        cartRepository.deleteById(cartId);
    }
    private CartProduct buildCartProduct(Integer quantity, Product product) {
        log.info("Building cart product");
       return CartProduct.builder()
                .cartProductId(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .category(product.getCategory())
                .quantity(quantity)
                .price(product.getPrice())
                .build();
    }

    private CartProduct buildCartProductFromAlreadyAvailableCartProduct(Integer quantity, CartProduct product) {
        log.info("Building cart product from already available cart");
        return CartProduct.builder()
                .cartProductId(product.getCartProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .category(product.getCategory())
                .quantity(quantity + product.getQuantity())
                .price(product.getPrice())
                .build();
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

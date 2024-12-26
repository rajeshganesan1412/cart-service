package com.customer.cart.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductNotFoundException extends  RuntimeException {

    private String errorMessage;

    private HttpStatus httpStatus;
}

package com.customer.cart.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ClientConnectionException extends RuntimeException {

    private String errorMessage;

    private HttpStatusCode httpStatus;
}

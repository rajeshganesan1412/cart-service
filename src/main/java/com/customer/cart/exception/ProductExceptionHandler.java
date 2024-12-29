package com.customer.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        String path =  request.getDescription(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseBuilder(path, ex.getHttpStatus(), ex.getErrorMessage()));
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePCartNotFoundException(CartNotFoundException ex, WebRequest request) {
        String path =  request.getDescription(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseBuilder(path, ex.getHttpStatus(), ex.getErrorMessage()));
    }
    @ExceptionHandler(ClientConnectionException.class)
    public ResponseEntity<ErrorResponse> handleClientConnectionException(ClientConnectionException ex, WebRequest request) {
        String path =  request.getDescription(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseBuilder(path, HttpStatus.valueOf(ex.getHttpStatus().value()), ex.getErrorMessage()));
    }
    @ExceptionHandler(ProductQuantityOverflowException.class)
    public ResponseEntity<ErrorResponse> handlePProductQuantityOverflowException(ProductQuantityOverflowException ex, WebRequest request) {
        String path =  request.getDescription(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseBuilder(path, ex.getHttpStatus(), ex.getErrorMessage()));
    }
    private ErrorResponse errorResponseBuilder(String url, HttpStatus httpCode, String errorMessage) {
        return ErrorResponse.builder().httpStatus(httpCode.toString()).url(url).errorMessage(errorMessage).build();
    }
}

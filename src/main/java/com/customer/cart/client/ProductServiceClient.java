package com.customer.cart.client;

import com.customer.cart.exception.ClientConnectionException;
import com.customer.cart.exception.ErrorResponse;
import com.customer.cart.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceClient {


    private final WebClient.Builder webClientBuilder;

    @Value("${product.service.url}")
    private String productServiceUrl;


    public Product getProductFromProductService(Long productId) {
        return getWebclient(productServiceUrl, productId)
                .get()
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(Product.class)
                .block();
    }

    private WebClient getWebclient(String url, Long productId) {
        return webClientBuilder
                .clone()
                .baseUrl(url+"/product/"+productId)
                .build();
    }

    private Mono<Throwable> handleError(ClientResponse clientResponse) {
        log.info("Handling communication error");
        return clientResponse
                .bodyToMono(ErrorResponse.class)
                .flatMap(errorspec -> {
                    log.error("{} exception occurred while calling product service", clientResponse.statusCode());
                    return Mono.error(new ClientConnectionException(
                            "exception occurred while calling product service", clientResponse.statusCode()));
                });
    }
}

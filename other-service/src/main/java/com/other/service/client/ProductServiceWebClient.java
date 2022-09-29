package com.other.service.client;

import com.other.service.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class ProductServiceWebClient {

    public final WebClient productWebClient;

    public Mono<ProductDTO> getProductById(final String productId) {
        return productWebClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }

}

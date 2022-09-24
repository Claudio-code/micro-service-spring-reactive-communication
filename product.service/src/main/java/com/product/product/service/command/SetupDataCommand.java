package com.product.product.service.command;

import com.product.product.service.dto.ProductDTO;
import com.product.product.service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class SetupDataCommand implements CommandLineRunner {

    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        final var product1 = ProductDTO
                    .builder()
                    .description("4k-tv")
                    .price(1000)
                    .build();

        final var product2 = ProductDTO
                    .builder()
                    .description("slr-camera")
                    .price(750)
                    .build();

        final var product3 = ProductDTO
                    .builder()
                    .description("iphone")
                    .price(800)
                    .build();

        final var product4 = ProductDTO
                    .builder()
                    .description("headphone")
                    .price(100)
                    .build();

        Flux.just(product1, product2, product3, product4)
                .map(Mono::just)
                .flatMap(productService::insertProduct)
                .subscribe(System.out::println);
    }
    
}

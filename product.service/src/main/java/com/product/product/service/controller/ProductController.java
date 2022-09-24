package com.product.product.service.controller;

import com.product.product.service.dto.ProductDTO;
import com.product.product.service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<ProductDTO> getAll() {
        return productService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDTO>> getProductById(@PathVariable final String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable final String id) {
        return productService.deleteProduct(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<ProductDTO> insertProduct(@RequestBody final Mono<ProductDTO> productDTOMono) {
        return productService.insertProduct(productDTOMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@PathVariable final String id,
                                                          @RequestBody final Mono<ProductDTO> productDTOMono) {
        return productService.updateProduct(id, productDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}

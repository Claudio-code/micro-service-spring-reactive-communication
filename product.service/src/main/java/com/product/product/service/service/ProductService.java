package com.product.product.service.service;

import com.product.product.service.dto.ProductDTO;
import com.product.product.service.factory.ProductDTOFactory;
import com.product.product.service.factory.ProductFactory;
import com.product.product.service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<ProductDTO> getAll() {
        return productRepository.findAll()
                .map(ProductDTOFactory::make)
                .doOnComplete(() -> log.info("M=Founded all products"));
    }

    public Mono<ProductDTO> getProductById(final String id) {
        return productRepository.findById(id)
                .map(ProductDTOFactory::make)
                .doOnSuccess(productDTO -> log.info("M=ProductFounded productID {}", productDTO.id()))
                .doOnError(throwable -> log.error("M=ProductNotFounded productID {} | error message {}", id, throwable.getMessage()));
    }

    public Mono<ProductDTO> insertProduct(final Mono<ProductDTO> productDTOMono) {
        return productDTOMono
                .map(ProductFactory::make)
                .flatMap(productRepository::insert)
                .map(ProductDTOFactory::make)
                .doOnSuccess(productDTO -> log.info("M=ProductSalved product salved with success productID {}", productDTO.id()))
                .doOnError(throwable -> log.error("M=ProductNotSalved error when tried save it product {} | error message {}", productDTOMono, throwable.getMessage()));
    }

    public Mono<ProductDTO> updateProduct(final String id, final Mono<ProductDTO> productDTOToUpdateMono) {
        return getProductById(id)
                .flatMap(productDTOFounded -> productDTOToUpdateMono
                        .map(productDTO -> ProductFactory.make(id, productDTO)))
                .flatMap(productRepository::save)
                .map(ProductDTOFactory::make)
                .doOnSuccess(productDTO -> log.info("M=ProductUpdated product updated with success {}", productDTO))
                .doOnError(throwable -> log.error("M=ProductNotUpdated error when tried update product data productId {} | error message {}", id, throwable.getMessage()));
    }

    public Mono<Void> deleteProduct(final String id) {
        return productRepository.deleteById(id)
                .doOnSuccess(unused -> log.info("M=ProductDeleted with success product id {}", id))
                .doOnError(throwable -> log.error("M=ProductDeleted with it error message {}", throwable.getMessage()));
    }

}

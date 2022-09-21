package com.product.product.service.factory;

import com.product.product.service.dto.ProductDTO;
import com.product.product.service.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductFactory {

    public static Product make(final ProductDTO productDTO) {
        return Product
                .builder()
                .id(productDTO.id())
                .description(productDTO.description())
                .price(productDTO.price())
                .build();
    }

    public static Product make(final String id, final ProductDTO productDTO) {
        return Product
                .builder()
                .id(id)
                .description(productDTO.description())
                .price(productDTO.price())
                .build();
    }

}

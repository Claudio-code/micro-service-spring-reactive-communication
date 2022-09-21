package com.product.product.service.factory;

import com.product.product.service.dto.ProductDTO;
import com.product.product.service.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDTOFactory {

    public static ProductDTO make(final Product product) {
        return ProductDTO
                .builder()
                .id(product.getId())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

}

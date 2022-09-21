package com.product.product.service.dto;

import lombok.Builder;

@Builder
public record ProductDTO(String id, String description, Integer price) {
}

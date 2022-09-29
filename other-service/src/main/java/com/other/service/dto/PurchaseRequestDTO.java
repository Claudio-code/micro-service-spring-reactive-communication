package com.other.service.dto;

import lombok.Builder;

@Builder
public record PurchaseRequestDTO(Integer userId, String productId) {
}

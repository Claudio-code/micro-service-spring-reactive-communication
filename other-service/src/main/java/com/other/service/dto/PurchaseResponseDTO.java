package com.other.service.dto;

import com.other.service.enuns.OrderStatus;
import lombok.Builder;

@Builder
public record PurchaseResponseDTO(Integer orderId, Integer userId,
                                  String productId, Integer amount, OrderStatus status) {
}

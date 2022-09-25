package com.user.service.dto;

import lombok.Builder;

@Builder
public record UserTransactionRequestDTO(Integer userId, Integer amount) {
}

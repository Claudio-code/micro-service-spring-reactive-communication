package com.user.service.dto;

import com.user.service.enuns.TransactionStatus;
import lombok.Builder;

@Builder
public record UserTransactionResponseDTO(Integer userId, Integer amount, TransactionStatus status) {
}

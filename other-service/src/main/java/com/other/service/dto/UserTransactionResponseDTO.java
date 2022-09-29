package com.other.service.dto;

import com.other.service.enuns.TransactionStatus;
import lombok.Builder;

@Builder
public record UserTransactionResponseDTO(Integer userId, Integer amount, TransactionStatus status) {
}

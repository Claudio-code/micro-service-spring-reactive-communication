package com.user.service.factory;

import com.user.service.dto.UserTransactionRequestDTO;
import com.user.service.dto.UserTransactionResponseDTO;
import com.user.service.entity.UserTransaction;
import com.user.service.enuns.TransactionStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTransactionDTOFactory {

    public static UserTransactionResponseDTO make(final UserTransaction userTransaction, TransactionStatus status) {
        return UserTransactionResponseDTO
                .builder()
                .userId(userTransaction.getUserId())
                .amount(userTransaction.getAmount())
                .status(status)
                .build();
    }

    public static UserTransactionResponseDTO make(final UserTransactionRequestDTO userTransactionRequestDTO, TransactionStatus status) {
        return UserTransactionResponseDTO
                .builder()
                .userId(userTransactionRequestDTO.userId())
                .amount(userTransactionRequestDTO.amount())
                .status(status)
                .build();
    }

}

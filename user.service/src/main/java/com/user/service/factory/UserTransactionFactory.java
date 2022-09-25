package com.user.service.factory;

import com.user.service.dto.UserTransactionRequestDTO;
import com.user.service.entity.UserTransaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTransactionFactory {

    public static UserTransaction make(final UserTransactionRequestDTO userTransactionRequestDTO) {
        return UserTransaction
                .builder()
                .userId(userTransactionRequestDTO.userId())
                .amount(userTransactionRequestDTO.amount())
                .transactionDate(LocalDateTime.now())
                .build();
    }

    public static UserTransaction make(final Integer userTransactionId,
                                       final UserTransactionRequestDTO userTransactionRequestDTO) {
        return UserTransaction
                .builder()
                .id(userTransactionId)
                .userId(userTransactionRequestDTO.userId())
                .amount(userTransactionRequestDTO.amount())
                .transactionDate(LocalDateTime.now())
                .build();
    }

}

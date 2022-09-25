package com.user.service.service;

import com.user.service.dto.UserTransactionRequestDTO;
import com.user.service.dto.UserTransactionResponseDTO;
import com.user.service.entity.UserTransaction;
import com.user.service.enuns.TransactionStatus;
import com.user.service.factory.UserTransactionDTOFactory;
import com.user.service.factory.UserTransactionFactory;
import com.user.service.repository.UserRepository;
import com.user.service.repository.UserTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserTransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;

    public Mono<UserTransactionResponseDTO> createTransaction(final UserTransactionRequestDTO userTransactionRequestDTO) {
         return userRepository.updateUserBalance(userTransactionRequestDTO.userId(), userTransactionRequestDTO.amount())
                .filter(Boolean::booleanValue)
                .map(booleanValue -> UserTransactionFactory.make(userTransactionRequestDTO))
                .flatMap(userTransactionRepository::save)
                .map(userTransaction -> UserTransactionDTOFactory.make(userTransaction, TransactionStatus.APPROVED))
                .defaultIfEmpty(UserTransactionDTOFactory.make(userTransactionRequestDTO, TransactionStatus.DECLINED));
    }

}

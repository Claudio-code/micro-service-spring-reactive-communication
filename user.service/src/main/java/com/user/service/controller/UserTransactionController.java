package com.user.service.controller;

import com.user.service.dto.UserTransactionRequestDTO;
import com.user.service.dto.UserTransactionResponseDTO;
import com.user.service.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

    private final UserTransactionService userTransactionService;

    @PostMapping
    public Mono<UserTransactionResponseDTO> createTransaction(
            @RequestBody final Mono<UserTransactionRequestDTO> userTransactionRequestDTOMono) {
        return userTransactionRequestDTOMono.flatMap(userTransactionService::createTransaction);
    }

}

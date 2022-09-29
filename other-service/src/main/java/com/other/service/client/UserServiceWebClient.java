package com.other.service.client;

import com.other.service.dto.UserTransactionRequestDTO;
import com.other.service.dto.UserTransactionResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class UserServiceWebClient {

    public final WebClient userWebClient;

    public Mono<UserTransactionResponseDTO> authorizeTransaction(UserTransactionRequestDTO userTransactionResponseDTO) {
        return userWebClient
                .post()
                .uri("user/transaction")
                .bodyValue(userTransactionResponseDTO)
                .retrieve()
                .bodyToMono(UserTransactionResponseDTO.class);
    }

}

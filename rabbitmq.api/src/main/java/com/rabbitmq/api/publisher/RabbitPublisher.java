package com.rabbitmq.api.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.api.dto.BodyDTO;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import javax.annotation.PreDestroy;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitPublisher implements CommandLineRunner {

    static final String QUEUE = "new_randow-queue";
    Mono<Connection> connectionMono;
    final Sender sender;
    final Flux<Delivery> deliveryFlux;

    @Override
    public void run(String... args) throws JsonProcessingException, InterruptedException {
        final var objectMapper = new ObjectMapper();
        final var messageDTO = BodyDTO
                .builder()
                .id(UUID.randomUUID().toString())
                .body("My message")
                .build();

        final var messageDTOBytes = objectMapper.writeValueAsBytes(messageDTO);
        sender.send(Mono.just(new OutboundMessage("", QUEUE, messageDTOBytes)))
                .doOnSuccess(unused -> log.info("Message sent"))
                .retryWhen(connectionFailure())
                .onErrorResume(error -> {
                    log.error(QUEUE, error);
                    return Mono.empty();
                })
                .subscribe();
    }


    private static RetryBackoffSpec connectionFailure() {
        return Retry.backoff(10L, Duration.ofMinutes(1))
                .doBeforeRetry(arg0 -> log.error("before Retrying due to exception...", arg0.failure()))
                .doAfterRetry(arg0 -> log.error("Retrying due to exception...", arg0.failure()));
    }


    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }

}

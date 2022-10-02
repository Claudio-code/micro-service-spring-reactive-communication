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

import javax.annotation.PreDestroy;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
        int messageCount = 10;
        CountDownLatch latch = new CountDownLatch(messageCount);
        final var messageDTOBytes = objectMapper.writeValueAsBytes(messageDTO);
        final var message = Flux.range(1, messageCount)
                .map(i -> new OutboundMessage("", QUEUE, messageDTOBytes));

        sender.send(message)
                .doOnNext(unused -> log.info("Message sent"))
                .subscribe(unused -> log.info("Message sent"));

        latch.await(10L, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }

}

package com.rabbitmq.api.consumer;


import com.rabbitmq.client.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.ConsumeOptions;
import reactor.rabbitmq.Receiver;

import javax.annotation.PreDestroy;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitConsumer implements CommandLineRunner {

    static final String QUEUE = "new_randow-queue";
    Mono<Connection> connectionMono;
    final Receiver receiver;


    @Override
    public void run(String... args) throws Exception {
        int messageCount = 10;
        CountDownLatch latch = new CountDownLatch(messageCount);

        Disposable disposable = receiver.consumeNoAck(QUEUE, new ConsumeOptions()).subscribe(m -> {
            log.info("Received message {}", new String(m.getBody()));
        });

        //Wait for threads to complete
        latch.await(3L, TimeUnit.SECONDS);

        //Close receiver and tasks
        disposable.dispose();
        receiver.close();
    }

    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }

}

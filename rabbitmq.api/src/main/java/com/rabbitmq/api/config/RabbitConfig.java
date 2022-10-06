package com.rabbitmq.api.config;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.ListAddressResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.addresses}")
    String address;

    static final String QUEUE = "new_randow-queue";

    @Bean
    Mono<Connection> connectionMono() {
        final var connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();

        return Mono.fromCallable(() -> connectionFactory
                .newConnection(parserAddress()))
                .cache();
    }

    @Bean
    public SenderOptions senderOptions(Mono<Connection> connectionMono) {
        return new SenderOptions().connectionMono(connectionMono);
    }

    @Bean
    public Sender sender(SenderOptions senderOptions) {
        return RabbitFlux.createSender(senderOptions);
    }


    @Bean
    public ReceiverOptions receiverOptions(Mono<Connection> connectionMono) {
        return new ReceiverOptions()
                .connectionMono(connectionMono);
    }

    @Bean
    Receiver receiver(ReceiverOptions receiverOptions) {
        return RabbitFlux.createReceiver(receiverOptions);
    }

    @Bean
    Flux<Delivery> deliveryFlux(Receiver receiver) {
        return receiver.consumeNoAck(QUEUE);
    }

    private ListAddressResolver parserAddress() {
        final List<Address> addressList = Stream.of(address.split(","))
                .map(Address::new)
                .toList();
        return new ListAddressResolver(addressList);
    }

}

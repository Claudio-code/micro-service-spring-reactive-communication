package com.other.service.service;

import com.other.service.client.ProductServiceWebClient;
import com.other.service.client.UserServiceWebClient;
import com.other.service.dto.PurchaseRequestDTO;
import com.other.service.dto.PurchaseResponseDTO;
import com.other.service.dto.RequestContext;
import com.other.service.factory.PurchaseOrderFactory;
import com.other.service.factory.PurchaseResponseDTOFactory;
import com.other.service.factory.UserTransactionDTOFactory;
import com.other.service.repository.PurchaseOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
@Service
public class OrderFulfillmentService {

    private final ProductServiceWebClient productServiceWebClient;
    private final UserServiceWebClient userServiceWebClient;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public Mono<PurchaseResponseDTO> processOrder(final Mono<PurchaseRequestDTO> purchaseRequestDTOMono) {
        return purchaseRequestDTOMono
                .map(purchaseRequestDTO -> new RequestContext(purchaseRequestDTO))
                .flatMap(this::productRequestResponse)
                .doOnNext(this::setTransactionInRequestContext)
                .flatMap(this::userRequestResponse)
                .map(PurchaseOrderFactory::make)
                .map(purchaseOrderRepository::save) // blocking process
                .map(PurchaseResponseDTOFactory::make)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private void setTransactionInRequestContext(RequestContext requestContext) {
        final var userTransactionDTO = UserTransactionDTOFactory.make(requestContext);
        requestContext.setUserTransactionRequestDTO(userTransactionDTO);
    }

    private Mono<RequestContext> productRequestResponse(RequestContext context) {
        return productServiceWebClient
                .getProductById(context.getPurchaseRequestDTO().productId())
                .doOnNext(context::setProductDTO)
                .thenReturn(context);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return userServiceWebClient
                .authorizeTransaction(requestContext.getUserTransactionRequestDTO())
                .doOnNext(requestContext::setUserTransactionResponseDTO)
                .thenReturn(requestContext);
    }

}

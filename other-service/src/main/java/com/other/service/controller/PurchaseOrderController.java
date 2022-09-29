package com.other.service.controller;

import com.other.service.dto.PurchaseRequestDTO;
import com.other.service.dto.PurchaseResponseDTO;
import com.other.service.service.OrderFulfillmentService;
import com.other.service.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class PurchaseOrderController {

    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService orderQueryService;

    @PostMapping
    public Mono<PurchaseResponseDTO> order(@RequestBody final Mono<PurchaseRequestDTO> purchaseRequestDTOMono) {
        return orderFulfillmentService.processOrder(purchaseRequestDTOMono);
    }

    @GetMapping("user/{userId}")
    public Flux<PurchaseResponseDTO> getOrderByUserId(@PathVariable final Integer userId) {
        return orderQueryService.getProductsByUserId(userId);
    }

}

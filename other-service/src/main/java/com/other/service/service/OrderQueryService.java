package com.other.service.service;

import com.other.service.dto.PurchaseResponseDTO;
import com.other.service.factory.PurchaseResponseDTOFactory;
import com.other.service.repository.PurchaseOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
@Service
public class OrderQueryService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public Flux<PurchaseResponseDTO> getProductsByUserId(final Integer userId) {
        return Flux.fromStream(() -> purchaseOrderRepository.findByUserId(userId).stream())
                .map(PurchaseResponseDTOFactory::make)
                .subscribeOn(Schedulers.boundedElastic());
    }

}

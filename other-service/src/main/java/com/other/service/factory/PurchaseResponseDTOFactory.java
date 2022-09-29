package com.other.service.factory;

import com.other.service.dto.PurchaseResponseDTO;
import com.other.service.entity.PurchaseOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseResponseDTOFactory {

    public static PurchaseResponseDTO make(PurchaseOrder purchaseOrder) {
        return PurchaseResponseDTO
                .builder()
                .userId(purchaseOrder.getUserId())
                .status(purchaseOrder.getStatus())
                .amount(purchaseOrder.getAmount())
                .orderId(purchaseOrder.getId())
                .productId(purchaseOrder.getProductId())
                .build();
    }

}

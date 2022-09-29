package com.other.service.factory;

import com.other.service.dto.RequestContext;
import com.other.service.entity.PurchaseOrder;
import com.other.service.enuns.OrderStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseOrderFactory {

    public static PurchaseOrder make(RequestContext requestContext) {
        final var purchaseOrderRequestDTO = requestContext.getPurchaseRequestDTO();
        final var transactionStatus = requestContext.getUserTransactionResponseDTO().status();
        final var orderStatus = OrderStatus.createOrderStatus(transactionStatus);
        return PurchaseOrder
                .builder()
                .userId(purchaseOrderRequestDTO.userId())
                .productId(purchaseOrderRequestDTO.productId())
                .amount(requestContext.getProductDTO().price())
                .status(orderStatus)
                .build();
    }

}

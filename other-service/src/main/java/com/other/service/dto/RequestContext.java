package com.other.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestContext {

    private PurchaseRequestDTO purchaseRequestDTO;
    private ProductDTO productDTO;
    private UserTransactionRequestDTO userTransactionRequestDTO;
    private UserTransactionResponseDTO userTransactionResponseDTO;


    public RequestContext(PurchaseRequestDTO purchaseRequestDTO) {
        this.purchaseRequestDTO = purchaseRequestDTO;
    }

}

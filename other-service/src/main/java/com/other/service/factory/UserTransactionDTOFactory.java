package com.other.service.factory;

import com.other.service.dto.RequestContext;
import com.other.service.dto.UserTransactionRequestDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTransactionDTOFactory {

    public static UserTransactionRequestDTO make(RequestContext requestContext) {
        return UserTransactionRequestDTO
                .builder()
                .userId(requestContext.getPurchaseRequestDTO().userId())
                .amount(requestContext.getProductDTO().price())
                .build();
    }

}

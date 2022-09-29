package com.other.service.enuns;

public enum OrderStatus {
    COMPLETED,
    FAILED;

    public static OrderStatus createOrderStatus(TransactionStatus transactionStatus) {
        return TransactionStatus.APPROVED.equals(transactionStatus) ?
                OrderStatus.COMPLETED : OrderStatus.FAILED;
    }

}

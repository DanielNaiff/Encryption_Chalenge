package com.DanielNaiff.encryption.entity.dto;

import com.DanielNaiff.encryption.entity.TransactionEntity;

public record TransactionResponseDTO(Long id, String useDocument, String creditCardToken, Long value) {

    public static TransactionResponseDTO fromEntity(TransactionEntity transactionEntity){
        return new TransactionResponseDTO(
                transactionEntity.getId(),
                transactionEntity.getUserDocument(),
                transactionEntity.getCreditCardToken(),
                transactionEntity.getTransactionValue()
        );
    }
}

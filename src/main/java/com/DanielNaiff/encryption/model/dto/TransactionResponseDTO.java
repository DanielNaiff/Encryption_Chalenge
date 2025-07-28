package com.DanielNaiff.encryption.model.dto;

import com.DanielNaiff.encryption.model.TransactionEntity;

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

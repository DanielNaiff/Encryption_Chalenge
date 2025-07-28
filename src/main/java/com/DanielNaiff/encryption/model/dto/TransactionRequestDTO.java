package com.DanielNaiff.encryption.model.dto;

public record TransactionRequestDTO(String userDocument, String creditCardToken, Long userValue) {
}

package com.DanielNaiff.encryption.entity.dto;

public record TransactionRequestDTO(String userDocument, String creditCardToken, Long userValue) {
}

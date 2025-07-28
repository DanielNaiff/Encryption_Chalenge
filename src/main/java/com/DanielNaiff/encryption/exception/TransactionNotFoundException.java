package com.DanielNaiff.encryption.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id) {
        super("Transação com ID " + id + " não encontrada.");
    }
}

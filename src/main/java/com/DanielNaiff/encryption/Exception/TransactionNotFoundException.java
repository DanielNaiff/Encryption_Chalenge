package com.DanielNaiff.encryption.Exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id) {
        super("Transação com ID " + id + " não encontrada.");
    }
}

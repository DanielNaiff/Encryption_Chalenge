package com.DanielNaiff.encryption.repository;

import com.DanielNaiff.encryption.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}

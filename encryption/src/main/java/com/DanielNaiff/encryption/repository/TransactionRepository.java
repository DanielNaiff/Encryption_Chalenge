package com.DanielNaiff.encryption.repository;

import com.DanielNaiff.encryption.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}

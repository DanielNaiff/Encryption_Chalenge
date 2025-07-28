package com.DanielNaiff.encryption.model;

import com.DanielNaiff.encryption.security.converter.AESConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_transactions")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_document", nullable = false, unique = true)
    @Convert(converter = AESConverter.class)
    private String userDocument;

    @Column(name = "credit_card_token", nullable = false)
    @Convert(converter = AESConverter.class)
    private String creditCardToken;

    @Column(name="user_value")
    private Long transactionValue;
}

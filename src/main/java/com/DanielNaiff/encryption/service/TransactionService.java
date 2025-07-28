package com.DanielNaiff.encryption.service;


import com.DanielNaiff.encryption.exception.MissingFieldException;
import com.DanielNaiff.encryption.exception.TransactionNotFoundException;
import com.DanielNaiff.encryption.model.TransactionEntity;
import com.DanielNaiff.encryption.model.dto.TransactionRequestDTO;
import com.DanielNaiff.encryption.model.dto.TransactionResponseDTO;
import com.DanielNaiff.encryption.model.dto.TransactionUpdateRequestDTO;
import com.DanielNaiff.encryption.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionRepository transactionRepository;

    public void create(TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO == null) {
            throw new MissingFieldException("O corpo da requisição está vazio.");
        }

        if (transactionRequestDTO.creditCardToken() == null || transactionRequestDTO.creditCardToken().isBlank()) {
            throw new MissingFieldException("O campo creditCardToken é obrigatório.");
        }

        if (transactionRequestDTO.userDocument() == null || transactionRequestDTO.userDocument().isBlank()) {
            throw new MissingFieldException("O campo userDocument é obrigatório.");
        }

        if (transactionRequestDTO.userValue() == null || transactionRequestDTO.userValue().doubleValue() <= 0) {
            throw new MissingFieldException("O campo userValue é obrigatório e deve ser maior que zero.");
        }

        TransactionEntity transaction = new TransactionEntity();
        transaction.setUserDocument(transactionRequestDTO.userDocument());
        transaction.setTransactionValue(transactionRequestDTO.userValue());
        transaction.setCreditCardToken(transactionRequestDTO.creditCardToken());

        transactionRepository.save(transaction);
    }

    public Page<TransactionResponseDTO> listAll(int page, int pageSize) {
        var content = transactionRepository.findAll(PageRequest.of(page, pageSize));
        return content.map(TransactionResponseDTO::fromEntity);
    }

    public TransactionResponseDTO findById(Long id){
        TransactionEntity transactionEntity = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));

        return TransactionResponseDTO.fromEntity(transactionEntity);
    }

    public void update(Long id, TransactionUpdateRequestDTO transactionUpdateRequestDTO){
        TransactionEntity transactionEntity = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));

        transactionEntity.setTransactionValue(transactionUpdateRequestDTO.value());
        transactionRepository.save(transactionEntity);
    }

    public void delete(Long id){

        TransactionEntity transactionEntity = transactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND)));

        transactionRepository.delete(transactionEntity);
    }
}

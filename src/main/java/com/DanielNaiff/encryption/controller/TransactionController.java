package com.DanielNaiff.encryption.controller;

import com.DanielNaiff.encryption.model.dto.TransactionRequestDTO;
import com.DanielNaiff.encryption.model.dto.TransactionResponseDTO;
import com.DanielNaiff.encryption.model.dto.TransactionUpdateRequestDTO;
import com.DanielNaiff.encryption.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @PostMapping(value = "/create")
    public ResponseEntity<Void> create(@RequestBody TransactionRequestDTO transactionRequestDTO){
        transactionService.create(transactionRequestDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Page<TransactionResponseDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize){
        var body = transactionService.listAll(page, pageSize);

        return ResponseEntity.ok(body);
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id){
        var body = transactionService.findById(id);

        return ResponseEntity.ok(body);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable(value = "id") Long id, @RequestBody TransactionUpdateRequestDTO transactionUpdateRequestDTO){
        transactionService.update(id, transactionUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id){
        transactionService.delete(id);

        return ResponseEntity.ok().build();
    }
}

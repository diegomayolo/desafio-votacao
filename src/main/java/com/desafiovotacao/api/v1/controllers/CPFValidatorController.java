package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.client.dtos.FakeCPFDTO;
import com.desafiovotacao.api.v1.facades.FakeCPFValidator;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cpf")
public class CPFValidatorController {
    private final FakeCPFValidator fakeCPFValidator;

    @PostMapping("/validate/{cpf}")
    public ResponseEntity<Object> validateCpf(@PathVariable String cpf) {
        try {
            FakeCPFDTO result = fakeCPFValidator.getValidCPF(cpf);
            return ResponseEntity.ok().body(result);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.contentUTF8());
        }
    }
}

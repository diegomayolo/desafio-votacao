package com.desafiovotacao.api.v1.client.controllers;

import com.desafiovotacao.api.v1.client.dtos.FakeCPFDTO;
import com.desafiovotacao.api.v1.client.services.FakeCPFService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class FakeCPFController {

    private final FakeCPFService service;

    @PostMapping("/client/cpf/validate/{cpf}")
    public ResponseEntity<FakeCPFDTO> validateCPF(@PathVariable String cpf) {
        ResponseEntity<FakeCPFDTO> validCpf = service.isValidCpf(cpf);
        
        if (validCpf.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(validCpf.getBody());
        }
        
        return ResponseEntity.ok().body(validCpf.getBody());
    }
}

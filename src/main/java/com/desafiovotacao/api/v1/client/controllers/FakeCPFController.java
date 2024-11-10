package com.desafiovotacao.api.v1.client.controllers;

import com.desafiovotacao.api.v1.client.dtos.FakeCPFDTO;
import com.desafiovotacao.api.v1.client.services.FakeCPFService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/client/cpf")
public class FakeCPFController {
    private final FakeCPFService service;

    @PostMapping("/validate/{cpf}")
    public ResponseEntity<FakeCPFDTO> validateCPF(@PathVariable String cpf) {
        return service.isValidCpf(cpf);
    }
}

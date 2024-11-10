package com.desafiovotacao.api.v1.client.controllers;

import com.desafiovotacao.api.v1.client.dtos.FakeCPFDTO;
import com.desafiovotacao.api.v1.client.services.FakeCPFService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "CPF - Serviço Externo Simulado",
        description = "Simula um serviço externo para validação de CPFs, retornando informações de elegibilidade para votação."
)
@RestController
@AllArgsConstructor
@RequestMapping("/client/cpf")
public class FakeCPFController {
    private final FakeCPFService service;

    @Operation(
            summary = "Validar CPF",
            description = "Este endpoint valida um CPF."
    )
    @ApiResponse(
            responseCode = "200",
            description = "CPF válido e elegível para votação",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FakeCPFDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "CPF inválido ou não elegível para votação",
            content = @Content(mediaType = "text/plain",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "CPF inválido")
            )
    )
    @PostMapping("/validate/{cpf}")
    public ResponseEntity<FakeCPFDTO> validateCPF(@PathVariable String cpf) {
        return service.isValidCpf(cpf);
    }
}

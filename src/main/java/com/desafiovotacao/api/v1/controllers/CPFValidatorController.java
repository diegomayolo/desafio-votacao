package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.client.dtos.FakeCPFDTO;
import com.desafiovotacao.api.v1.facades.FakeCPFValidator;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "CPF - Facade",
        description = "Gerencia a elegibilidade de votação consumindo um serviço externo simulado."
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cpf")
public class CPFValidatorController {
    private final FakeCPFValidator fakeCPFValidator;

    @Operation(
            summary = "Validar CPF",
            description = "Este endpoint valida um CPF por meio de um serviço externo simulado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "CPF validado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FakeCPFDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "CPF inválido",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "O número informado não é válido. Informe um CPF ou CNPJ válido, " +
                                                      "sem pontos, traços ou barras.")
            )
    )
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

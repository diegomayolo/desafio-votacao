package com.desafiovotacao.api.v1.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AssociateResponseDTO(
        @Schema(description = "Identificador do associado", example = "1")
        Integer id,
        @Schema(description = "Nome do associado", example = "Usuário Teste")
        String name,
        @Schema(description = "CPF do associado", example = "XXXXXXXXXXX")
        String cpf,
        @Schema(description = "Data de criação do associado", example = "2021-10-10T10:00:00")
        LocalDateTime createdAt) {
}


package com.desafiovotacao.api.v1.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;

public record VoteResponseDTO(
        @Schema(description = "Identificador do voto", example = "1")
        Integer id,
        @Schema(description = "Identificador do associado", example = "1")
        Integer associateId,
        @Schema(description = "Identificador da pauta", example = "1")
        Integer agendaId, 
        @Schema(description = "Voto do associado", example = "true")
        Boolean vote) {
}

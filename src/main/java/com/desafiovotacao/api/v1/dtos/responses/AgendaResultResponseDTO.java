package com.desafiovotacao.api.v1.dtos.responses;

import com.desafiovotacao.api.v1.enums.AgendaStateEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AgendaResultResponseDTO(
        @Schema(description = "Identificador da pauta", example = "1")
        Integer agendaId,
        @Schema(description = "Quantidade de votos a favor", example = "10")
        long approveVotes, 
        @Schema(description = "Quantidade de votos contra", example = "5")
        long rejectedVotes, 
        @Schema(description = "Quantidade total de votos", example = "15")
        long totalVotes, 
        
        @Schema(description = "Resultado da votação", example = "APPROVED")
        @JsonProperty("state") 
        AgendaStateEnum result) {
}

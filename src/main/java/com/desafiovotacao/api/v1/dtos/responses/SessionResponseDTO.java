package com.desafiovotacao.api.v1.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SessionResponseDTO(
        @Schema(description = "Identificador da pauta", example = "1")
        Integer agendaId,
        @Schema(description = "Duração da sessão em minutos", example = "1")
        Integer duration,
        @Schema(description = "Data de início da sessão", example = "2021-10-10T10:00:00")
        LocalDateTime startTime,
        @Schema(description = "Data de término da sessão", example = "2021-10-10T10:01:00")
        LocalDateTime endTime) {
}

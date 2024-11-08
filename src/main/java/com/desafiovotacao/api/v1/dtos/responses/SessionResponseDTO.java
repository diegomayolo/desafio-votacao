package com.desafiovotacao.api.v1.dtos.responses;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record SessionResponseDTO(Integer agendaId, Integer duration, LocalDateTime startTime, LocalDateTime endTime) {
}

package com.desafiovotacao.api.v1.dtos.responses;

import java.time.LocalDateTime;

public record SessionResponseDTO(Integer agendaId, Integer duration, LocalDateTime startTime, LocalDateTime endTime) {
}

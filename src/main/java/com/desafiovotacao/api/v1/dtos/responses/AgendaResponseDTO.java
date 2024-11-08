package com.desafiovotacao.api.v1.dtos.responses;

import lombok.Builder;

@Builder
public record AgendaResponseDTO(Integer id, String title, String description) {
}

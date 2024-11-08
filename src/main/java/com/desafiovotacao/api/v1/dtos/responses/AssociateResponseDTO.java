package com.desafiovotacao.api.v1.dtos.responses;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AssociateResponseDTO(Integer id, String name, String cpf, LocalDateTime createdAt) {
}


package com.desafiovotacao.api.v1.dtos.responses;

import java.time.LocalDateTime;

public record AssociateResponseDTO(Integer id, String name, String cpf, LocalDateTime createdAt) {
}


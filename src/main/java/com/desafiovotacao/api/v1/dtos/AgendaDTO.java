package com.desafiovotacao.api.v1.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AgendaDTO(
    @NotBlank(message = "O preenchimento do título é obrigatório")
    String title,
    String description
) {}

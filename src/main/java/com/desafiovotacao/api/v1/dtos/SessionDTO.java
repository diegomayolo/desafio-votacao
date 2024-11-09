package com.desafiovotacao.api.v1.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SessionDTO(
    @Schema(description = "Identificador da pauta em votação na sessão", example = "1")
    @NotNull(message = "É necessário informar o id da pauta")
    Integer agendaId,

    @Schema(description = "Duração da sessão de votação (em minutos)", 
            example = "60")
    @Min(value = 1, message = "A duração da assembleia deve ser de no mínimo 1 minuto")
    Integer duration
) {}

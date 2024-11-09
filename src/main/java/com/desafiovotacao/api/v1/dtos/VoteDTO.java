package com.desafiovotacao.api.v1.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteDTO(
    @Schema(description = "Resultado do voto", example = "true")
    Boolean vote,

    @Schema(description = "Identificador do associado votante", example = "1")
    @NotNull(message = "É necessário informar o id do associado")
    Integer associateId,

    @Schema(description = "Identificador da pauta votada", example = "1")
    @NotNull(message = "É necessário informar o id da pauta")
    Integer agendaId
) {}

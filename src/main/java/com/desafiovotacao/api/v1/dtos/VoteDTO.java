package com.desafiovotacao.api.v1.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteDTO(
    Boolean vote,

    @NotNull(message = "É necessário informar o id do associado")
    Integer associateId,

    @NotNull(message = "É necessário informar o id da pauta")
    Integer agendaId
) {}

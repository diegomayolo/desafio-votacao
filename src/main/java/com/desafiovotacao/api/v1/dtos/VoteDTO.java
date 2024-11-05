package com.desafiovotacao.api.v1.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {
    private Boolean vote;
    @NotNull(message = "É necessário informar o id do associado")
    private Integer associateId;
    @NotNull(message = "É necessário informar o id da pauta")
    private Integer agendaId;
}

package com.desafiovotacao.api.v1.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    @NotNull(message = "É necessário informar o id da pauta")
    private Integer agendaId;
    @Min(value = 1, message = "A duração da assembleia deve ser de no mínimo 1 minuto" )
    private Integer duration;
}

package com.desafiovotacao.api.v1.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AgendaResponseDTO(
        @Schema(description = "Identificador da pauta", example = "1")
        Integer id,
        @Schema(description = "Título da pauta", example = "Aprovação de Orçamento Anual")
        String title,
        @Schema(description = "Descrição da pauta", example = "Esta pauta visa a aprovação do orçamento anual da cooperativa, " +
                "incluindo alocações de recursos para investimentos, custos operacionais e iniciativas estratégicas.")
        String description) {
}

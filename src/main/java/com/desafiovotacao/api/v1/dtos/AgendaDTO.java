package com.desafiovotacao.api.v1.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AgendaDTO(
    @Schema(description = "Título da pauta", example = "Aprovação de Orçamento Anual")
    @NotBlank(message = "O preenchimento do título é obrigatório")
    String title,
    
    @Schema(description = "Esta pauta visa a aprovação do orçamento anual da cooperativa, " +
                  "incluindo alocações de recursos para investimentos, custos operacionais e iniciativas estratégicas.", 
            example = "Aprovação de Orçamento Anual")
    String description
) {}

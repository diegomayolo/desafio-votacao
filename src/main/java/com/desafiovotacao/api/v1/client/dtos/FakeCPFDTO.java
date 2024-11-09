package com.desafiovotacao.api.v1.client.dtos;

import com.desafiovotacao.api.v1.enums.VotingEligibilityEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record FakeCPFDTO(
    @Schema(description = "Status da validação do CPF simulado", example = "ABLE_TO_VOTE")
    VotingEligibilityEnum status) {
}

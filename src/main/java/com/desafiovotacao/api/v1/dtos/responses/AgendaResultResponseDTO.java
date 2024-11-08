package com.desafiovotacao.api.v1.dtos.responses;

import com.desafiovotacao.api.v1.enums.AgendaStateEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AgendaResultResponseDTO(Integer agendaId, long approveVotes, long rejectedVotes, long totalVotes, @JsonProperty("state") AgendaStateEnum result) {
}

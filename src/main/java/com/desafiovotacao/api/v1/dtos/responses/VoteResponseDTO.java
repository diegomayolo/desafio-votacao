package com.desafiovotacao.api.v1.dtos.responses;

public record VoteResponseDTO(Integer id, Integer associateId, Integer agendaId, Boolean vote) {
}

package com.desafiovotacao.api.v1.mappers;

import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;

public class AgendaMapper {
    public static AgendaResponseDTO toResponseDTO(AgendaEntity entity) {
        return new AgendaResponseDTO(entity.getId(),
                                     entity.getTitle(),
                                     entity.getDescription());
    }
}

package com.desafiovotacao.api.v1.mappers;

import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;

import java.util.List;

public class AgendaMapper {

    private AgendaMapper() {}

    public static AgendaResponseDTO toResponseDTO(AgendaEntity entity) {
        return new AgendaResponseDTO(entity.getId(),
                                     entity.getTitle(),
                                     entity.getDescription());
    }

    public static List<AgendaResponseDTO> toResponseDTOList(List<AgendaEntity> agendas) {
        return agendas.stream().map(AgendaMapper::toResponseDTO).toList();
    }
}

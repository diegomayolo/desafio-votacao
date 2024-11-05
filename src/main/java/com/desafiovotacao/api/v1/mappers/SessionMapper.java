package com.desafiovotacao.api.v1.mappers;

import com.desafiovotacao.api.v1.dtos.responses.SessionResponseDTO;
import com.desafiovotacao.api.v1.entities.SessionEntity;

public class SessionMapper {
    public static SessionResponseDTO toResponseDTO(SessionEntity entity) {
        return new SessionResponseDTO(entity.getId(),
                                      entity.getDuration(),
                                      entity.getStartTime(),
                                      entity.getEndTime());
    }
}

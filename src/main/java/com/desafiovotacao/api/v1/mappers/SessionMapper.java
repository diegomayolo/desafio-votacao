package com.desafiovotacao.api.v1.mappers;

import com.desafiovotacao.api.v1.dtos.responses.SessionResponseDTO;
import com.desafiovotacao.api.v1.entities.SessionEntity;

import java.util.List;

public class SessionMapper {
    public static SessionResponseDTO toResponseDTO(SessionEntity session) {
        return new SessionResponseDTO(session.getId(),
                                      session.getDuration(),
                                      session.getStartTime(),
                                      session.getEndTime());
    }

    public static List<SessionResponseDTO> toResponseDTOList(List<SessionEntity> sessions) {
        return sessions.stream().map(SessionMapper::toResponseDTO).toList();
    }
}

package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.SessionDTO;
import com.desafiovotacao.api.v1.dtos.responses.SessionResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.SessionEntity;
import com.desafiovotacao.api.v1.exceptions.AgendaNotFoundException;
import com.desafiovotacao.api.v1.exceptions.ActiveSessionException;
import com.desafiovotacao.api.v1.mappers.SessionMapper;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final AgendaRepository agendaRepository;
    
    public SessionResponseDTO create(SessionDTO sessionDTO) {
        checkExistingAgenda(sessionDTO);
        checkActiveSessions(sessionDTO);

        SessionEntity entity = sessionRepository.save(SessionEntity.builder()
                                                                   .agendaId(sessionDTO.agendaId())
                                                                   .duration(sessionDTO.duration())
                                                                   .build());

        return SessionMapper.toResponseDTO(entity);
    }

    private void checkExistingAgenda(SessionDTO sessionDTO) {
        Optional<AgendaEntity> agendaOptional = agendaRepository.findById(sessionDTO.agendaId());

        if (agendaOptional.isEmpty()) {
            throw new AgendaNotFoundException();
        }
    }

    private void checkActiveSessions(SessionDTO sessionDTO) {
        List<SessionEntity> activeSessions = sessionRepository.findByAgendaIdAndEndTimeAfter(sessionDTO.agendaId(), LocalDateTime.now());

        if (!activeSessions.isEmpty()) {
            throw new ActiveSessionException();
        }
    }
}

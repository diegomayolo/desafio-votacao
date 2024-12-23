package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.SessionDTO;
import com.desafiovotacao.api.v1.dtos.responses.SessionResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.SessionEntity;
import com.desafiovotacao.api.v1.exceptions.ActiveSessionException;
import com.desafiovotacao.api.v1.exceptions.AgendaNotFoundException;
import com.desafiovotacao.api.v1.exceptions.SessionNotFoundException;
import com.desafiovotacao.api.v1.mappers.SessionMapper;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final AgendaRepository agendaRepository;
    
    public SessionResponseDTO create(SessionDTO sessionDTO) {
        AgendaEntity agenda = checkExistingAgenda(sessionDTO);
        checkActiveSessions(sessionDTO);

        SessionEntity session = sessionRepository.save(SessionEntity.builder()
                                                                    .agenda(agenda)
                                                                    .duration(sessionDTO.duration())
                                                                    .build());

        return SessionMapper.toResponseDTO(session);
    }

    public SessionResponseDTO findById(Integer sessionId) {
        SessionEntity session = sessionRepository.findById(sessionId)
                                                 .orElseThrow(SessionNotFoundException::new);
        
        return SessionMapper.toResponseDTO(session);
    }

    public List<SessionResponseDTO> listAll() {
        return SessionMapper.toResponseDTOList(sessionRepository.findAll());
    }
    
    private AgendaEntity checkExistingAgenda(SessionDTO sessionDTO) {
        return agendaRepository.findById(sessionDTO.agendaId())
                               .orElseThrow(AgendaNotFoundException::new);
    }

    private void checkActiveSessions(SessionDTO sessionDTO) {
        List<SessionEntity> activeSessions = sessionRepository.findByAgendaIdAndEndTimeAfter(sessionDTO.agendaId(), LocalDateTime.now());

        if (!activeSessions.isEmpty()) {
            throw new ActiveSessionException();
        }
    }
}

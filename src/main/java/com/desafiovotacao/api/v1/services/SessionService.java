package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.SessionDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.SessionEntity;
import com.desafiovotacao.api.v1.exceptions.AgendaNotFound;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository assemblyRepository;
    private final AgendaRepository agendaRepository;
    
    public SessionEntity create(SessionDTO sessionDTO) {
        Optional<AgendaEntity> agendaOptional = agendaRepository.findById(sessionDTO.getAgendaId());
        
        if (agendaOptional.isEmpty()) {
            throw new AgendaNotFound();
        }

        return assemblyRepository.save( SessionEntity.builder()
                                                      .agendaId(sessionDTO.getAgendaId())
                                                      .duration(sessionDTO.getDuration())
                                                      .build() );
    }
}

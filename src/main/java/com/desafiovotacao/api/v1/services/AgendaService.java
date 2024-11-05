package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.AgendaDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;
    
    public AgendaEntity create(AgendaDTO agendaDTO) {
        AgendaEntity agendaEntity = AgendaEntity.builder()
                                                .title(agendaDTO.getTitle())
                                                .description(agendaDTO.getDescription())
                                                .build();
        
        return agendaRepository.save(agendaEntity);
    }
}

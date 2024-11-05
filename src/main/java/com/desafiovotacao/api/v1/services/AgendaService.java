package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.AgendaDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.mappers.AgendaMapper;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;
    
    public AgendaResponseDTO create(AgendaDTO agendaDTO) {
        AgendaEntity entity = agendaRepository.save(AgendaEntity.builder()
                                                                .title(agendaDTO.title())
                                                                .description(agendaDTO.description())
                                                                .build());
        
        return AgendaMapper.toResponseDTO(entity);
    }
}

package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.AgendaDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResultResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.VoteEntity;
import com.desafiovotacao.api.v1.enums.AgendaStateEnum;
import com.desafiovotacao.api.v1.exceptions.AgendaNotFoundException;
import com.desafiovotacao.api.v1.mappers.AgendaMapper;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;
    private final VoteRepository voteRepository;
    
    public AgendaResponseDTO create(AgendaDTO agendaDTO) {
        AgendaEntity entity = agendaRepository.save(AgendaEntity.builder()
                                                                .title(agendaDTO.title())
                                                                .description(agendaDTO.description())
                                                                .build());
        
        return AgendaMapper.toResponseDTO(entity);
    }


    public AgendaResultResponseDTO getResult(Integer agendaId) {
        if (!agendaRepository.existsById(agendaId)) {
            throw new AgendaNotFoundException();
        }
        
        List<VoteEntity> entities = voteRepository.findByAgendaId(agendaId);

        long approveVotes = entities.stream().filter(v -> v.getVote()).count();
        long rejectedVotes = entities.stream().filter(v -> !v.getVote()).count();

        AgendaStateEnum result;

        if (approveVotes > rejectedVotes) {
            result = AgendaStateEnum.APPROVED;
        } else if (approveVotes < rejectedVotes) {
            result = AgendaStateEnum.REJECTED;
        } else {
            result = AgendaStateEnum.TIE;
        }

        return new AgendaResultResponseDTO(agendaId,
                                           approveVotes,
                                           rejectedVotes,
                                           entities.size(),
                                           result);
    }

    public AgendaResponseDTO findById(Integer agendaId) {
        AgendaEntity agenda = agendaRepository.findById(agendaId)
                                              .orElseThrow(AgendaNotFoundException::new);

        return AgendaMapper.toResponseDTO(agenda);
    }
    
    public List<AgendaResponseDTO> listAll() {
        List<AgendaEntity> agendas = agendaRepository.findAll();

        return AgendaMapper.toResponseDTOList(agendas);
    }
}

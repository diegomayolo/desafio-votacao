package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.VoteDTO;
import com.desafiovotacao.api.v1.dtos.responses.VoteResponseDTO;
import com.desafiovotacao.api.v1.entities.VoteEntity;
import com.desafiovotacao.api.v1.mappers.VoteMapper;
import com.desafiovotacao.api.v1.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    
    public VoteResponseDTO create(VoteDTO voteDTO) {
        VoteEntity entity = voteRepository.save(VoteEntity.builder()
                                                          .vote(voteDTO.vote())
                                                          .associateId(voteDTO.associateId())
                                                          .agendaId(voteDTO.agendaId())
                                                          .build());
        
        return VoteMapper.toResponseDTO(entity);
    }
}

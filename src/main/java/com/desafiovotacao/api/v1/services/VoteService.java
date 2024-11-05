package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.VoteDTO;
import com.desafiovotacao.api.v1.entities.VoteEntity;
import com.desafiovotacao.api.v1.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    
    public VoteEntity create(VoteDTO voteDTO) {
       return voteRepository.save(VoteEntity.builder().vote(voteDTO.getVote())
                                                       .associateId(voteDTO.getAssociateId())
                                                       .agendaId(voteDTO.getAgendaId())
                                                       .build());
    }
}

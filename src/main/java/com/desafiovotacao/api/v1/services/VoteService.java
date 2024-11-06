package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.VoteDTO;
import com.desafiovotacao.api.v1.dtos.responses.VoteResponseDTO;
import com.desafiovotacao.api.v1.entities.VoteEntity;
import com.desafiovotacao.api.v1.exceptions.AlreadyVoteException;
import com.desafiovotacao.api.v1.mappers.VoteMapper;
import com.desafiovotacao.api.v1.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    
    public VoteResponseDTO create(VoteDTO voteDTO) {
        checkAssociateAlreadyVoteInAgenda(voteDTO);

        VoteEntity entity = voteRepository.save(VoteEntity.builder()
                                                          .vote(voteDTO.vote())
                                                          .associateId(voteDTO.associateId())
                                                          .agendaId(voteDTO.agendaId())
                                                          .build());
        
        return VoteMapper.toResponseDTO(entity);
    }

    private void checkAssociateAlreadyVoteInAgenda(VoteDTO voteDTO) {
        if (voteRepository.findByAssociateIdAndAgendaId(voteDTO.associateId(), voteDTO.agendaId()).isPresent()) {
            throw new AlreadyVoteException();
        }
    }
}

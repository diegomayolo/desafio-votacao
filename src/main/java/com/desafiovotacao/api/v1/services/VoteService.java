package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.VoteDTO;
import com.desafiovotacao.api.v1.dtos.responses.VoteResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
import com.desafiovotacao.api.v1.entities.VoteEntity;
import com.desafiovotacao.api.v1.exceptions.AgendaNotFoundException;
import com.desafiovotacao.api.v1.exceptions.AlreadyVoteException;
import com.desafiovotacao.api.v1.exceptions.AssociateNotFoundException;
import com.desafiovotacao.api.v1.mappers.VoteMapper;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.AssociateRepository;
import com.desafiovotacao.api.v1.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final AssociateRepository associateRepository;
    private final AgendaRepository agendaRepository;
    
    public VoteResponseDTO create(VoteDTO voteDTO) {
        checkAssociateAlreadyVoteInAgenda(voteDTO);

        AssociateEntity associate = associateRepository.findById(voteDTO.associateId())
                                                       .orElseThrow(() -> new AssociateNotFoundException());
        
        AgendaEntity agenda = agendaRepository.findById(voteDTO.agendaId())
                                                       .orElseThrow(() -> new AgendaNotFoundException());

        VoteEntity entity = voteRepository.save(VoteEntity.builder()
                                                          .vote(voteDTO.vote())
                                                          .associate(associate)
                                                          .agenda(agenda)
                                                          .build());
        
        return VoteMapper.toResponseDTO(entity);
    }

    private void checkAssociateAlreadyVoteInAgenda(VoteDTO voteDTO) {
        if (voteRepository.findByAssociateIdAndAgendaId(voteDTO.associateId(), voteDTO.agendaId()).isPresent()) {
            throw new AlreadyVoteException();
        }
    }
}

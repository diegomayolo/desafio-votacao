package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.VoteDTO;
import com.desafiovotacao.api.v1.dtos.responses.VoteResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
import com.desafiovotacao.api.v1.entities.SessionEntity;
import com.desafiovotacao.api.v1.entities.VoteEntity;
import com.desafiovotacao.api.v1.exceptions.AgendaNotFoundException;
import com.desafiovotacao.api.v1.exceptions.AlreadyVoteException;
import com.desafiovotacao.api.v1.exceptions.AssociateNotFoundException;
import com.desafiovotacao.api.v1.exceptions.VotingSessionClosedException;
import com.desafiovotacao.api.v1.mappers.VoteMapper;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.AssociateRepository;
import com.desafiovotacao.api.v1.repositories.SessionRepository;
import com.desafiovotacao.api.v1.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final AssociateRepository associateRepository;
    private final SessionRepository sessionRepository;
    private final AgendaRepository agendaRepository;

    public VoteResponseDTO create(VoteDTO voteDTO) {
        AssociateEntity associate = associateRepository.findById(voteDTO.associateId())
                                                       .orElseThrow(AssociateNotFoundException::new);

        AgendaEntity agenda = agendaRepository.findById(voteDTO.agendaId())
                                              .orElseThrow(AgendaNotFoundException::new);

        validateVoteConditions(voteDTO);

        VoteEntity entity = voteRepository.save(VoteEntity.builder()
                                                          .vote(voteDTO.vote())
                                                          .associate(associate)
                                                          .agenda(agenda)
                                                          .build());

        return VoteMapper.toResponseDTO(entity);
    }

    private void validateVoteConditions(VoteDTO voteDTO) {
        checkAssociateAlreadyVoteInAgenda(voteDTO);
        votingSessionIsOpen(voteDTO);
    }

    private void votingSessionIsOpen(VoteDTO voteDTO) {
        SessionEntity session = sessionRepository.findByAgendaId(voteDTO.agendaId());
        if (session == null || session.getEndTime().isBefore(LocalDateTime.now())) {
            throw new VotingSessionClosedException();
        }
    }

    private void checkAssociateAlreadyVoteInAgenda(VoteDTO voteDTO) {
        voteRepository.findByAssociateIdAndAgendaId(voteDTO.associateId(), voteDTO.agendaId())
                      .ifPresent(vote -> { throw new AlreadyVoteException(); });
    }
}

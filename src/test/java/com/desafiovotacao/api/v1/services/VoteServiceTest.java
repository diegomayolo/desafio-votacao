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
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.AssociateRepository;
import com.desafiovotacao.api.v1.repositories.SessionRepository;
import com.desafiovotacao.api.v1.repositories.VoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoteServiceTest")
public class VoteServiceTest {
    public static final int MOCKED_ID = 1;
    
    public static final String MOCKED_NAME = "Associado Teste";
    public static final String MOCKED_CPF = "025.212.130-98";
    
    public static final String MOCKED_TITLE = "Título";
    public static final String MOCKED_DESCRIPTION = "Descrição";
    
    @InjectMocks
    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;
    
    @Mock
    private AssociateRepository associateRepository;
    
    @Mock
    private AgendaRepository agendaRepository;
    
    @Mock
    private SessionRepository sessionRepository;

    @Test
    @DisplayName("Deve criar o voto com sucesso")
    void createVote_Success_WhenValid() {
        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .id(MOCKED_ID)
                                                         .name(MOCKED_NAME)
                                                         .cpf(MOCKED_CPF)
                                                         .build();

        AgendaEntity agendaEntity = AgendaEntity.builder()
                                                .id(MOCKED_ID)
                                                .title(MOCKED_TITLE)
                                                .description(MOCKED_DESCRIPTION)
                                                .build();
        
        SessionEntity sessionEntity = SessionEntity.builder()
                                                   .agenda(agendaEntity)
                                                   .endTime(LocalDateTime.now().plusMinutes(30))
                                                   .build();

        VoteEntity voteEntity = VoteEntity.builder()
                                          .vote(true)
                                          .associate(associateEntity)
                                          .agenda(agendaEntity)
                                          .build();
        
        VoteDTO voteDTO = VoteDTO.builder()
                                 .vote(true)
                                 .associateId(MOCKED_ID)
                                 .agendaId(MOCKED_ID)
                                 .build();

        when(associateRepository.findById(MOCKED_ID)).thenReturn(Optional.of(associateEntity));
        when(agendaRepository.findById(MOCKED_ID)).thenReturn(Optional.of(agendaEntity));
        when(sessionRepository.findByAgendaId(MOCKED_ID)).thenReturn(sessionEntity);
        when(voteRepository.findByAssociateIdAndAgendaId(MOCKED_ID, MOCKED_ID)).thenReturn(Optional.empty());
        when(voteRepository.save(voteEntity)).thenReturn(voteEntity);

        VoteResponseDTO voteResponseDTO = voteService.create(voteDTO);

        Assertions.assertNotNull(voteResponseDTO);
    }

    @Test
    @DisplayName("Deve lançar AssociateNotFoundException se associado não for encontrado")
    void throwAssociateNotFoundException_WhenAssociateNotFound() {
        VoteDTO voteDTO = VoteDTO.builder()
                                 .vote(true)
                                 .associateId(MOCKED_ID)
                                 .agendaId(MOCKED_ID)
                                 .build();
        
        when(associateRepository.findById(MOCKED_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(AssociateNotFoundException.class, () -> voteService.create(voteDTO));
    }

    @Test
    @DisplayName("Deve lançar AgendaNotFoundException se agenda não for encontrada")
    void throwAgendaNotFoundException_WhenAgendaNotFound() {
        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .id(MOCKED_ID)
                                                         .name(MOCKED_NAME)
                                                         .cpf(MOCKED_CPF)
                                                         .build();
        
        VoteDTO voteDTO = VoteDTO.builder()
                                 .associateId(MOCKED_ID)
                                 .agendaId(MOCKED_ID)
                                 .vote(true)
                                 .build();
        
        when(associateRepository.findById(MOCKED_ID)).thenReturn(Optional.of(associateEntity));
        when(agendaRepository.findById(MOCKED_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(AgendaNotFoundException.class, () -> voteService.create(voteDTO));
    }

    @Test
    @DisplayName("Deve lançar VotingSessionClosedException se a sessão de votação estiver fechada")
    void throwVotingSessionClosedException_WhenSessionClosed() {
        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .id(MOCKED_ID)
                                                         .name(MOCKED_NAME)
                                                         .cpf(MOCKED_CPF)
                                                         .build();
        
        AgendaEntity agendaEntity = AgendaEntity.builder()
                                                .id(MOCKED_ID)
                                                .title(MOCKED_TITLE)
                                                .description(MOCKED_DESCRIPTION)
                                                .build();

        SessionEntity closedSession = SessionEntity.builder()
                                                   .agenda(agendaEntity)
                                                   .endTime(LocalDateTime.now().minusMinutes(MOCKED_ID))
                                                   .build();

        VoteDTO voteDTO = VoteDTO.builder()
                                 .associateId(MOCKED_ID)
                                 .agendaId(MOCKED_ID)
                                 .vote(true)
                                 .build();

        when(associateRepository.findById(MOCKED_ID)).thenReturn(Optional.of(associateEntity));
        when(agendaRepository.findById(MOCKED_ID)).thenReturn(Optional.of(agendaEntity));
        when(sessionRepository.findByAgendaId(MOCKED_ID)).thenReturn(closedSession);

        Assertions.assertThrows(VotingSessionClosedException.class, () -> voteService.create(voteDTO));
    }

    @Test
    @DisplayName("Deve lançar AlreadyVoteException se o associado já tiver votado na agenda")
    void throwAlreadyVoteException_WhenAssociateAlreadyVoted() {
        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .id(MOCKED_ID)
                                                         .name(MOCKED_NAME)
                                                         .cpf(MOCKED_CPF)
                                                         .build();

        AgendaEntity agendaEntity = AgendaEntity.builder()
                                                .id(MOCKED_ID)
                                                .title(MOCKED_TITLE)
                                                .description(MOCKED_DESCRIPTION)
                                                .build();

        VoteEntity voteEntity = VoteEntity.builder()
                                          .vote(true)
                                          .agenda(agendaEntity)
                                          .associate(associateEntity)
                                          .build();
        
        VoteDTO voteDTO = VoteDTO.builder()
                                 .vote(true)
                                 .associateId(MOCKED_ID)
                                 .agendaId(MOCKED_ID)
                                 .build();

        when(associateRepository.findById(MOCKED_ID)).thenReturn(Optional.of(associateEntity));
        when(agendaRepository.findById(MOCKED_ID)).thenReturn(Optional.of(agendaEntity));
        when(voteRepository.findByAssociateIdAndAgendaId(MOCKED_ID, MOCKED_ID)).thenReturn(Optional.of(voteEntity));

        Assertions.assertThrows(AlreadyVoteException.class, () -> voteService.create(voteDTO));
    }
}

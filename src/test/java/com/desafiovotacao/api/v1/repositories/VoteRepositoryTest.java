package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
import com.desafiovotacao.api.v1.entities.SessionEntity;
import com.desafiovotacao.api.v1.entities.VoteEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@DisplayName("VoteRepositoryTest")
public class VoteRepositoryTest {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AssociateRepository associateRepository;
    @Autowired
    private AgendaRepository agendaRepository;

    @Mock
    private VoteEntity voteEntity;
    @Mock
    private AssociateEntity associateEntity;
    @Mock
    private AgendaEntity agendaEntity;

    @BeforeEach
    void before() {
        associateEntity = associateRepository.save(AssociateEntity.builder()
                                                                  .name("Associado Teste")
                                                                  .cpf("025.212.130-98")
                                                                  .createdAt(LocalDateTime.now())
                                                                  .build());

        agendaEntity = agendaRepository.save(AgendaEntity.builder()
                                                         .title("Título")
                                                         .description("Descrição")
                                                         .build());

        voteEntity = voteRepository.save(VoteEntity.builder()
                                                   .vote(true)
                                                   .associate(associateEntity)
                                                   .agenda(agendaEntity)
                                                   .build());
    }
    
    @Test
    @DisplayName("Salvar um voto com sucesso")
    public void saveVote_Success_WhenVoteIsValid() {
        Assertions.assertNotNull(voteEntity.getId(), "O voto deveria ter um ID após ser salvo");
    }

    @Test
    @DisplayName("Encontrar o voto pelo associateId e agendaId")
    public void findVoteByAssociateIdAndAgendaId_Success_WhenVoteExists() {
        Optional<VoteEntity> optionalVote = voteRepository.findByAssociateIdAndAgendaId(associateEntity.getId(), agendaEntity.getId());

        Assertions.assertTrue(optionalVote.isPresent(), "O voto deveria ser encontrado pelo id do associado e id da pauta");
    }
    
    @Test
    @DisplayName("Não encontrar o voto quando associateId ou agendaId não existirem")
    public void findVoteByAssociateIdAndAgendaId_Fail_WhenVoteDoesNotExist() {
        Optional<VoteEntity> optionalVote = voteRepository.findByAssociateIdAndAgendaId(1, 1);

        Assertions.assertFalse(optionalVote.isPresent(), "O voto não deveria ser encontrado com os IDs inexistentes");
    }
}

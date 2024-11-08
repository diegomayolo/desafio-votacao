package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.SessionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("SessionRepositoryTest")
public class SessionRepositoryTest {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private AgendaRepository agendaRepository;

    @Mock
    private SessionEntity sessionEntity;
    @Mock
    private AgendaEntity agendaEntity;

    @BeforeEach
    void before() {
        agendaEntity = agendaRepository.save(AgendaEntity.builder()
                                                         .title("Título")
                                                         .description("Descrição")
                                                         .build());

        sessionEntity = sessionRepository.save(SessionEntity.builder()
                                                            .agenda(agendaEntity)
                                                            .duration(60)
                                                            .build());
    }

    @Test
    @DisplayName("Deve encontrar a sessão pelo ID")
    void findSessionById_Success_WhenSessionExists() {
        Optional<SessionEntity> sessionEntityById = sessionRepository.findById(sessionEntity.getId());

        Assertions.assertNotNull(sessionEntityById);
    }

    @Test
    @DisplayName("Deve encontrar a sessão pelo agendaId")
    void findSessionsByAgendaId_Success_WhenSessionsExist() {
        SessionEntity sessionEntity = sessionRepository.findByAgendaId(agendaEntity.getId());

        Assertions.assertNotNull(sessionEntity, "Sessão deveria ser encontrada para a pauta informada");
    }

    @Test
    @DisplayName("Deve encontrar sessões pelo agendaId e endTime após o horário atual")
    void findSessionsByAgendaIdAndEndTimeAfter_Success_WhenSessionsExist() {
        List<SessionEntity> sessions = sessionRepository.findByAgendaIdAndEndTimeAfter(agendaEntity.getId(), LocalDateTime.now().plusMinutes(10));

        Assertions.assertFalse(sessions.isEmpty(), "Sessão deveria ser encontrada para a pauta informada e com data de término após a data atual");
    }
}

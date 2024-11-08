package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.SessionDTO;
import com.desafiovotacao.api.v1.dtos.responses.SessionResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.SessionEntity;
import com.desafiovotacao.api.v1.exceptions.ActiveSessionException;
import com.desafiovotacao.api.v1.exceptions.AgendaNotFoundException;
import com.desafiovotacao.api.v1.exceptions.SessionNotFoundException;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.SessionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("SessionServiceTest")
public class SessionServiceTest {
    private static final Integer MOCKED_ID = 1;
    private static final Integer MOCKED_DURATION = 60;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    @DisplayName("Deve criar sessão com sucesso quando os dados são válidos")
    public void createSession_Success_WhenValidInput() {
        AgendaEntity agendaEntity = new AgendaEntity();
        agendaEntity.setId(MOCKED_ID);

        SessionEntity sessionEntity = buildSessionEntity();
        sessionEntity.setAgenda(agendaEntity);

        SessionDTO sessionDTO = new SessionDTO(MOCKED_ID, MOCKED_DURATION);

        when(agendaRepository.findById(MOCKED_ID)).thenReturn(Optional.of(agendaEntity));

        when(sessionRepository.save(any(SessionEntity.class))).thenReturn(sessionEntity);

        SessionResponseDTO responseDTO = sessionService.create(sessionDTO);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(MOCKED_DURATION, responseDTO.duration());
        Assertions.assertEquals(MOCKED_ID, responseDTO.agendaId());
    }

    @Test
    @DisplayName("Deve lançar AgendaNotFoundException se a agenda não for encontrada")
    public void createSession_Fail_WhenAgendaNotFound() {
        SessionDTO sessionDTO = new SessionDTO(MOCKED_ID, MOCKED_DURATION);

        when(agendaRepository.findById(MOCKED_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(AgendaNotFoundException.class, () -> sessionService.create(sessionDTO));
    }

    @Test
    @DisplayName("Deve lançar ActiveSessionException se já houver sessão ativa para a agenda")
    public void createSession_Fail_WhenActiveSessionExists() {
        AgendaEntity agendaEntity = buildAgendaEntity();
        SessionDTO sessionDTO = new SessionDTO(MOCKED_ID, MOCKED_DURATION);

        lenient().when(sessionRepository.findByAgendaIdAndEndTimeAfter(eq(MOCKED_ID), any(LocalDateTime.class)))
                 .thenReturn(List.of(new SessionEntity()));
        when(agendaRepository.findById(MOCKED_ID)).thenReturn(Optional.of(agendaEntity));

        Assertions.assertThrows(ActiveSessionException.class, () -> sessionService.create(sessionDTO));
    }

    @Test
    @DisplayName("Deve retornar a sessão quando o ID existe")
    public void findSessionById_Success_WhenSessionExists() {
        SessionEntity sessionEntity = buildSessionEntity();

        when(sessionRepository.findById(MOCKED_ID)).thenReturn(Optional.of(sessionEntity));

        SessionResponseDTO responseDTO = sessionService.findById(MOCKED_ID);

        Assertions.assertNotNull(responseDTO);
    }

    @Test
    @DisplayName("Deve lançar SessionNotFoundException se a sessão não for encontrada")
    public void findSessionById_Fail_WhenSessionNotFound() {
        when(sessionRepository.findById(999)).thenReturn(Optional.empty());

        Assertions.assertThrows(SessionNotFoundException.class, () -> sessionService.findById(999));
    }

    @Test
    @DisplayName("Deve listar todas as sessões com sucesso")
    public void listAllSessions_Success_WhenRequested() {
        List<SessionEntity> sessionEntities = List.of(buildSessionEntity(), buildSessionEntity());

        when(sessionRepository.findAll()).thenReturn(sessionEntities);

        List<SessionResponseDTO> responseDTOList = sessionService.listAll();

        Assertions.assertEquals(2, responseDTOList.size());
    }

    private SessionEntity buildSessionEntity() {
        AgendaEntity agendaEntity = buildAgendaEntity();
        return SessionEntity.builder()
                            .agenda(agendaEntity)
                            .duration(MOCKED_DURATION)
                            .endTime(LocalDateTime.now().plusMinutes(MOCKED_DURATION))
                            .build();
    }

    private AgendaEntity buildAgendaEntity() {
        return AgendaEntity.builder()
                           .id(MOCKED_ID) 
                           .title("Titulo")
                           .description("Descrição")
                           .build();
    }
}

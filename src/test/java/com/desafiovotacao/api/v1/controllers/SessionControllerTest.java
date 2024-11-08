package com.desafiovotacao.api.v1.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.desafiovotacao.api.v1.dtos.SessionDTO;
import com.desafiovotacao.api.v1.dtos.responses.SessionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("SessionControllerTest")
public class SessionControllerTest {
    private static final String BASE_URL = "/api/v1/session";

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SessionDTO sessionDTO;

    @Mock
    private SessionResponseDTO sessionResponseDTO;

    private ObjectMapper objectMapper;

    @BeforeEach
    void before() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                         .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        int agendaId = 1;
        int duration = 30;
        LocalDateTime date = LocalDateTime.of(2023, 11, 8, 07, 00, 00);

        sessionDTO = SessionDTO.builder().agendaId(agendaId)
                                         .duration(duration)
                                         .build();

        sessionResponseDTO = SessionResponseDTO.builder()
                                               .agendaId(agendaId)
                                               .duration(duration)
                                               .startTime(date)
                                               .endTime(date.plusMinutes(duration))
                                               .build();
    }

    @Test
    @DisplayName("Cria uma sessão com dados válidos")
    @Sql(scripts = "/db/testdata/insertAgenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createSession_Success_WhenDataIsValid() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                                      .content(objectMapper.writeValueAsString(sessionDTO)))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("Deve retornar a sessão quando o ID existe") 
    @Sql(scripts = "/db/testdata/insertAgenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertSession.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findSessionById_Success_WhenIdExists() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{sessionId}", 1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(content().json(objectMapper.writeValueAsString(sessionResponseDTO)));
    }

    @Test
    @DisplayName("Retorna erro 400 quando o ID da sessão não existe")
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findSessionById_BadRequest_WhenIdDoesNotExist() throws Exception {
        int nonExistentSessionId = 999;

        mockMvc.perform(get(BASE_URL + "/{sessionId}", nonExistentSessionId))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
               .andExpect(content().string("Sessão não encontrada"));
    }

    @Test
    @DisplayName("Retorna todos as sessões com sucesso")
    @Sql(scripts = "/db/testdata/insertAgenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertSession.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllSessions_Success_WhenRequested() throws Exception {
        mockMvc.perform(get(BASE_URL, 1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(content().json(objectMapper.writeValueAsString(List.of(sessionResponseDTO))));
    }
}

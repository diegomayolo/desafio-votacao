package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.VoteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("VoteControllerTest")
class VoteControllerTest {
    private static final String BASE_URL = "/api/v1/vote";
    
    @Autowired
    private MockMvc mockMvc;

    private VoteDTO voteDTO;

    private ObjectMapper objectMapper;

    @BeforeEach
    void before() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                         .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        int associateId = 1;
        int agendaId = 1;

        voteDTO = VoteDTO.builder().vote(true).associateId(associateId).agendaId(agendaId).build();
    }

    @Test
    @DisplayName("Cria um voto com os dados válidos")
    @Sql(scripts = "/db/testdata/insertAssociate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertAgenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertOpenSession.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createVote_Success_WhenDataIsValid() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                                      .content(objectMapper.writeValueAsString(voteDTO)))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("Não deve criar um voto se a sessão estiver encerrada")
    @Sql(scripts = "/db/testdata/insertAssociate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertAgenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertSession.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createVote_Fail_WhenSessionIsClosed() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                                      .content(objectMapper.writeValueAsString(voteDTO)))
               .andExpect(status().isBadRequest());
    }
}

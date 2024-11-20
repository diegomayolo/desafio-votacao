package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AgendaDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResultResponseDTO;
import com.desafiovotacao.api.v1.enums.AgendaStateEnum;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AgendaControllerTest")
class AgendaControllerTest {
    public static final String BASE_URL = "/api/v1/agenda";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private AgendaDTO agendaDTO;
    private AgendaResponseDTO agendaResponseDTO;
    private AgendaResultResponseDTO agendaResultResponseDTO;

    @BeforeEach
    void before() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                         .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String title = "Titulo";
        String description = "Descricao";

        agendaDTO = AgendaDTO.builder().title(title).description(description).build();

        agendaResponseDTO = AgendaResponseDTO.builder()
                                             .id(1)
                                             .title(title)
                                             .description(description)
                                             .build();

        agendaResultResponseDTO = AgendaResultResponseDTO.builder().agendaId(1)
                                                                   .approveVotes(1)
                                                                   .rejectedVotes(0)
                                                                   .totalVotes(1)
                                                                   .result(AgendaStateEnum.APPROVED)
                                                                   .build();
    }

    @Test
    @DisplayName("Cria pauta com sucesso quando os dados são válidos")
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createAgenda_Success_WhenDataIsValid() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                                      .content(objectMapper.writeValueAsString(agendaDTO)))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("Falha ao criar pauta com dados inválidos")
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createAgenda_Fail_WhenDataIsInvalid() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                                      .content(objectMapper.writeValueAsString(AgendaDTO.builder().build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Encontra pauta por ID com sucesso")
    @Sql(scripts = "/db/testdata/insertAgenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findaAgendaById_Success_WhenIdExists() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{agendaId}", 1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(content().json(objectMapper.writeValueAsString(agendaResponseDTO)));
    }

    @Test
    @DisplayName("Retorna erro 400 quando ID da pauta não existe")
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAgendaById_Fail_WhenIdDoesNotExist() throws Exception {
        int nonExistentAgendaId = 1;

        mockMvc.perform(get(BASE_URL + "/{agendaId}", nonExistentAgendaId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Pauta não encontrada"));
    }

    @Test
    @DisplayName("Retorna todos as pautas com sucesso")
    @Sql(scripts = "/db/testdata/insertAgenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllAgendas_Success_WhenRequested() throws Exception {
        mockMvc.perform(get(BASE_URL, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(agendaResponseDTO))));
    }

    @Test
    @DisplayName("Contar votos com sucesso")
    @Sql(scripts = "/db/testdata/insertAssociate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertAgenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertSession.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/insertVote.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void countVotes_Success_WhenVotesExist() throws Exception {
        mockMvc.perform(get(BASE_URL + "/result/{agendaId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(agendaResultResponseDTO)));
    }


    @Test
    @DisplayName("Retorna erro 400 ao contar votos de pauta inexistente")
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void countVotes_Fail_WhenAgendaDoesNotExist() throws Exception {
        mockMvc.perform(get(BASE_URL + "/result/{agendaId}", 1))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Pauta não encontrada"));
    }
}

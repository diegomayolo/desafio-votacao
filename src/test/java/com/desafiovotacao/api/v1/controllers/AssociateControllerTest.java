package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AssociateDTO;
import com.desafiovotacao.api.v1.dtos.responses.AssociateResponseDTO;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AssociateControllerTest")
public class AssociateControllerTest {

    public static final String BASE_URL = "/api/v1/associate";
    
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AssociateDTO associateDTO;
    private AssociateResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        final String name = "Associado Teste";
        final String cpf = "025.212.130-98";
        final LocalDateTime date = LocalDateTime.of(2023, 11, 8, 07, 00, 00);

        associateDTO = AssociateDTO.builder().name(name).cpf(cpf).build();
        responseDTO = AssociateResponseDTO.builder().id(1).name(name).cpf(cpf).createdAt(date).build();

        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                         .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    
    @Test
    @DisplayName("Criar associado com sucesso quando os dados são válidos")
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createAssociate_Success_WhenDataIsValid() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(responseDTO)))
               .andExpect(status().isCreated());
    }

   @Test
   @DisplayName("Falha ao criar associado com dados inválidos")
   @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
   public void createAssociate_Fail_WhenDataIsInvalid() throws Exception {
       associateDTO = AssociateDTO.builder().name("Associado Teste").cpf(null).build();
       
       mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                                     .content(objectMapper.writeValueAsString(associateDTO)))
              .andExpect(status().isBadRequest());
   }
   
   @Test
   @DisplayName("Encontra associado por ID com sucesso")
   @Sql(scripts = "/db/testdata/insertAssociate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
   @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
   public void findAssociateById_Success_WhenIdExists() throws Exception {
       mockMvc.perform(get(BASE_URL + "/{associateId}", 1)).andExpect(status().isOk())
                                                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) 
                                                                    .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
   }

    @Test
    @DisplayName("Retorna erro 400 quando ID do associado não existe")
    @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAssociateById_Fail_WhenIdDoesNotExist() throws Exception {
        int nonExistentAssociateId = 1;

        mockMvc.perform(get(BASE_URL + "/{associateId}", nonExistentAssociateId))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
               .andExpect(content().string("Associado não encontrado"));
    }

   @Test
   @DisplayName("Retorna todos os associados com sucesso")
   @Sql(scripts = "/db/testdata/insertAssociate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
   @Sql(scripts = "/db/testdata/resetDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
   public void findAllAssociates_Success_WhenRequested() throws Exception {
       mockMvc.perform(get(BASE_URL)).andExpect(status().isOk())
                                     .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                     .andExpect(content().json(objectMapper.writeValueAsString(List.of(responseDTO))));
   }
}

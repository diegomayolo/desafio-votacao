package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AssociateDTO;
import com.desafiovotacao.api.v1.dtos.responses.AssociateResponseDTO;
import com.desafiovotacao.api.v1.services.AssociateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AssociateController Test")
public class AssociateControllerTest {

    public static final String BASE_URL = "/api/v1/associate";
    
    private static final int MOCKED_ID1 = 1;
    private static final int MOCKED_ID2 = 2;
    private static final String MOCKED_NAME1 = "Usuário de Teste 1";
    private static final String MOCKED_CPF1 = "12345678910";
    private static final String MOCKED_NAME2 = "Usuário de Teste 2";
    private static final String MOCKED_CPF2 = "12345678911";
    private static final String MOCKED_CPF_INVALID = "000.000.000-00";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssociateService associateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar associado com sucesso quando dados são válidos")
    public void createAssociate_Success_WhenDataIsValid() throws Exception {
        AssociateDTO associateDTO = new AssociateDTO(MOCKED_NAME1, MOCKED_CPF1);
        AssociateResponseDTO responseDTO = new AssociateResponseDTO(MOCKED_ID1, MOCKED_NAME1, MOCKED_CPF1, LocalDateTime.now());

        when(associateService.create(associateDTO)).thenReturn(responseDTO);

        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                                      .content("{\"name\": \"" + MOCKED_NAME1 + "\", \"cpf\": \"" + MOCKED_CPF1 + "\"}"))
               .andExpect(status().isCreated())
               .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    @DisplayName("Falha ao criar associado com dados inválidos")
    public void createAssociate_Fail_WhenDataIsInvalid() throws Exception {
        when(associateService.create(any(AssociateDTO.class))).thenThrow(new RuntimeException("Erro ao criar associado"));

        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                                      .content("{\"name\": \"" + MOCKED_NAME1 + "\", \"cpf\": \"" + MOCKED_CPF_INVALID + "\"}"))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Encontra associado por ID com sucesso")
    public void findAssociateById_Success_WhenIdExists() throws Exception {
        AssociateResponseDTO responseDTO = new AssociateResponseDTO(MOCKED_ID1, MOCKED_NAME1, MOCKED_CPF1, LocalDateTime.now());

        when(associateService.findById(MOCKED_ID1)).thenReturn(responseDTO);
        
        mockMvc.perform(get(BASE_URL + "/{associateId}", MOCKED_ID1)).andExpect(status().isOk())
                                                                     .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) 
                                                                     .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    @DisplayName("Busca todos os associados com sucesso")
    public void findAllAssociates_Success_WhenRequested() throws Exception {
        AssociateResponseDTO responseDTO1 = new AssociateResponseDTO(MOCKED_ID1, MOCKED_NAME1, MOCKED_CPF1, LocalDateTime.now());
        AssociateResponseDTO responseDTO2 = new AssociateResponseDTO(MOCKED_ID2, MOCKED_NAME2, MOCKED_CPF2, LocalDateTime.now());

        when(associateService.listAll()).thenReturn(List.of(responseDTO1, responseDTO2));

        mockMvc.perform(get("/api/v1/associate")).andExpect(status().isOk())
                                                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) 
                                                 .andExpect(content().json(objectMapper.writeValueAsString(List.of(responseDTO1, responseDTO2))));
    }
}

package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.AssociateDTO;
import com.desafiovotacao.api.v1.dtos.responses.AssociateResponseDTO;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
import com.desafiovotacao.api.v1.exceptions.AssociateFoundException;
import com.desafiovotacao.api.v1.exceptions.AssociateNotFoundException;
import com.desafiovotacao.api.v1.repositories.AssociateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssociateService Test")
public class AssociateServiceTest {
    
    private static final int MOCKED_ID1 = 1;
    private static final String MOCKED_NAME1 = "Usuário de Teste 1";
    private static final String MOCKED_CPF1 = "12345678910";
    private static final String MOCKED_NAME2 = "Usuário de Teste 2";
    private static final String MOCKED_CPF2 = "12345678911";
    
    @Mock
    private AssociateRepository associateRepository;

    @InjectMocks
    private AssociateService associateService;

    @Test
    @DisplayName("Deve criar associado com sucesso quando dados são válidos")
    public void createAssociate_Success_WhenValidInput() {
        AssociateEntity associate = buildAssociate( MOCKED_NAME1, MOCKED_CPF1);
        AssociateDTO associateDTO = new AssociateDTO(MOCKED_NAME1, MOCKED_CPF1);
        
        when(associateRepository.findByCpf(MOCKED_CPF1)).thenReturn(Optional.empty());
        when(associateRepository.save(any(AssociateEntity.class))).thenReturn(associate);

        AssociateResponseDTO result = associateService.create(associateDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(MOCKED_NAME1, result.name());
        Assertions.assertEquals(MOCKED_CPF1, result.cpf());
    }

    @Test
    @DisplayName("Não deve criar associado quando CPF já existe")
    public void createAssociate_Fail_WhenCpfAlreadyExists() {
        AssociateDTO associateDTO = new AssociateDTO(MOCKED_NAME1, MOCKED_CPF1);

        when(associateRepository.findByCpf(MOCKED_CPF1)).thenReturn(Optional.of(new AssociateEntity()));

        Assertions.assertThrows(AssociateFoundException.class, () -> associateService.create(associateDTO));
    }

    @Test
    @DisplayName("Deve encontrar associado por ID quando ID existe")
    public void findAssociateById_Success_WhenIdExists() {
        AssociateEntity associateEntity = buildAssociate(MOCKED_NAME1, MOCKED_CPF1);

        when(associateRepository.findById(MOCKED_ID1)).thenReturn(Optional.of(associateEntity));

        AssociateResponseDTO result = associateService.findById(MOCKED_ID1);

        Assertions.assertEquals(MOCKED_NAME1, result.name());
        Assertions.assertEquals(MOCKED_CPF1, result.cpf());
    }

    @Test
    @DisplayName("Não deve encontrar associado por ID quando ID não existe")
    void findAssociateById_Fail_WhenIdNotFound() {
        when(associateRepository.findById(MOCKED_ID1)).thenReturn(Optional.empty());

        Assertions.assertThrows(AssociateNotFoundException.class, () -> associateService.findById(MOCKED_ID1));
    }

    @Test
    @DisplayName("Deve buscar todos os associados")
    public void findAllAssociates_Success_WhenRequested() {
        List<AssociateEntity> associateEntities = List.of(
                buildAssociate(MOCKED_NAME1, MOCKED_CPF1),
                buildAssociate(MOCKED_NAME2, MOCKED_CPF2)
        );

        when(associateRepository.findAll()).thenReturn(associateEntities);

        List<AssociateResponseDTO> result = associateService.listAll();

        Assertions.assertEquals(2, result.size());
    }
    
    private AssociateEntity buildAssociate(String name, String cpf) {
        return AssociateEntity.builder().name(name).cpf(cpf).build();
    }
}

package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.AssociateEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("AssociateRepository Test")
@RequiredArgsConstructor
class AssociateRepositoryTest {

    private static final String MOCKED_NAME1 = "Usuário de Teste 1";
    private static final String MOCKED_CPF1 = "12345678910";
    private static final String MOCKED_NAME2 = "Usuário de Teste 2";
    private static final String MOCKED_CPF2 = "12345678911";
    
    @Autowired
    private AssociateRepository associateRepository;

    @Test
    @DisplayName("Encontrar o associado pelo ID")
    void findAssociateById_Success_WhenIdExists() {
        AssociateEntity associate = associateRepository.save(AssociateEntity.builder()
                                                                            .name(MOCKED_NAME1)
                                                                            .cpf(MOCKED_CPF1)
                                                                            .build());

        Optional<AssociateEntity> optional = associateRepository.findById(associate.getId());

        Assertions.assertTrue(optional.isPresent(), "O associado deveria ser encontrado pelo ID");
    }

    @Test
    @DisplayName("Não encontrar o associado quando o ID não existir")
    void findAssociateById_Fail_WhenIdDoesNotExist() {
        Optional<AssociateEntity> optional = associateRepository.findById(1);

        Assertions.assertFalse(optional.isPresent(), "O associado não deveria ser encontrado com o ID inexistente");
    }

    @Test
    @DisplayName("Buscar todos os associados")
    void findAllAssociates_Success_WhenRequested() {
        associateRepository.save(AssociateEntity.builder()
                                                .name(MOCKED_NAME1)
                                                .cpf(MOCKED_CPF1)
                                                .build());

        associateRepository.save(AssociateEntity.builder()
                                                .name(MOCKED_NAME2)
                                                .cpf(MOCKED_CPF2)
                                                .build());

        List<AssociateEntity> associates = associateRepository.findAll();

        Assertions.assertEquals(2, associates.size(), "O número de associados deveria ser 2");
    }
}

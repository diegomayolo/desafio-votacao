package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.AgendaEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("AgendaRepositoryTest")
@RequiredArgsConstructor
public class AgendaRepositoryTest {
    private static final String MOCKED_TITLE = "Título";
    private static final String MOCKED_DESCRIPTION = "Descrição";

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    @DisplayName("Encontrar o pauta pelo ID")
    public void findAgendaById_Success_WhenIdExists() {
        AgendaEntity agenda = agendaRepository.save(AgendaEntity.builder()
                                                                      .title(MOCKED_TITLE)
                                                                      .description(MOCKED_DESCRIPTION)
                                                                      .build());

        Optional<AgendaEntity> optional = agendaRepository.findById(agenda.getId());

        Assertions.assertTrue(optional.isPresent(), "A pauta deveria ser encontrada pelo ID");
    }

    @Test
    @DisplayName("Não encontrar a pauta quando o ID não existir")
    public void findAgendaById_Fail_WhenIdDoesNotExist() {
        Optional<AgendaEntity> optional = agendaRepository.findById(1);

        Assertions.assertFalse(optional.isPresent(), "A pauta não deveria ser encontrada com o ID inexistente");
    }

    @Test
    @DisplayName("Deve buscar todas as pautas")
    public void findAllAgendas_Success_WhenRequested() {
        agendaRepository.save(AgendaEntity.builder()
                                          .title(MOCKED_TITLE)
                                          .description(MOCKED_DESCRIPTION)
                                          .build());

        agendaRepository.save(AgendaEntity.builder()
                                          .title(MOCKED_TITLE)
                                          .description(MOCKED_DESCRIPTION)
                                          .build());

        List<AgendaEntity> agendas = agendaRepository.findAll();

        Assertions.assertEquals(2, agendas.size(), "Duas pautas deveriam ter sido encontradas");
    }
}

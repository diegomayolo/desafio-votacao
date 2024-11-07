package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.AgendaDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResultResponseDTO;
import com.desafiovotacao.api.v1.entities.AgendaEntity;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
import com.desafiovotacao.api.v1.entities.VoteEntity;
import com.desafiovotacao.api.v1.enums.AgendaStateEnum;
import com.desafiovotacao.api.v1.exceptions.AgendaNotFoundException;
import com.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.desafiovotacao.api.v1.repositories.VoteRepository;
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
@DisplayName("AgendaServiceTest")
public class AgendaServiceTest {
    private static final String MOCKED_TITLE = "Título";
    private static final String MOCKED_DESCRIPTION = "Descrição";

    @Mock
    private AgendaRepository agendaRepository;
    
    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private AgendaService agendaService;

    @Test
    @DisplayName("Deve criar pauta com sucesso quando os dados são válidos")
    public void createAgenda_Success_WhenValidInput() {
        AgendaEntity agenda = buildAgendaEntity();
        AgendaDTO agendaDTO = new AgendaDTO(MOCKED_TITLE, MOCKED_DESCRIPTION);

        when(agendaRepository.save(any(AgendaEntity.class))).thenReturn(agenda);

        AgendaResponseDTO responseDTO = agendaService.create(agendaDTO);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(MOCKED_TITLE, responseDTO.title());
        Assertions.assertEquals(MOCKED_DESCRIPTION, responseDTO.description());
    }

    @Test
    @DisplayName("Deve encontrar pauta por ID quando ID existe")
    public void findAgendaById_Success_WhenIdExists() {
        AgendaEntity agendaEntity = buildAgendaEntity();

        when(agendaRepository.findById(1)).thenReturn(Optional.of(agendaEntity));

        AgendaResponseDTO responseDTO = agendaService.findById(1);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(MOCKED_TITLE, responseDTO.title());
        Assertions.assertEquals(MOCKED_DESCRIPTION, responseDTO.description());
    }

    @Test
    @DisplayName("Não deve encontrar pauta por ID quando ID não existe")
    void findAgendaById_Fail_WhenIdNotFound() {
        Integer invalidAgendaId = 999;
        when(agendaRepository.findById(invalidAgendaId)).thenReturn(Optional.empty());

        Assertions.assertThrows(AgendaNotFoundException.class, () -> agendaService.findById(999));
    }

    @Test
    @DisplayName("Deve buscar todas as pautas")
    public void findAllAgendas_Success_WhenRequested() {
        List<AgendaEntity> agendaEntities = List.of(
            buildAgendaEntity(),
            buildAgendaEntity()
        );

        when(agendaRepository.findAll()).thenReturn(agendaEntities);

        List<AgendaResponseDTO> responseDTOS = agendaService.listAll();

        Assertions.assertEquals(2, responseDTOS.size());
    }

    @Test
    @DisplayName("Deve retornar o resultado da pauta quando o ID existe")
    public void getResult_Success_WhenAgendaIdExists() {
        Integer agendaId = 1;
        List<VoteEntity> voteEntities = List.of(
                VoteEntity.builder().vote(true).agenda(new AgendaEntity()).associate(new AssociateEntity()).build(),
                VoteEntity.builder().vote(false).agenda(new AgendaEntity()).associate(new AssociateEntity()).build(),
                VoteEntity.builder().vote(true).agenda(new AgendaEntity()).associate(new AssociateEntity()).build()
        );

        when(agendaRepository.existsById(agendaId)).thenReturn(true);
        when(voteRepository.findByAgendaId(agendaId)).thenReturn(voteEntities);

        AgendaResultResponseDTO result = agendaService.getResult(agendaId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(agendaId, result.agendaId());
        Assertions.assertEquals(2, result.approveVotes());
        Assertions.assertEquals(1, result.rejectedVotes());
        Assertions.assertEquals(3, result.totalVotes());
        Assertions.assertEquals(AgendaStateEnum.APPROVED, result.result());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID da pauta não existe")
    public void getResult_Fail_WhenIdDoesNotExist() {
        Integer invalidAgendaId = 999;
        when(agendaRepository.existsById(invalidAgendaId)).thenReturn(false);

        Assertions.assertThrows(AgendaNotFoundException.class, () -> agendaService.getResult(invalidAgendaId));
    }

    private AgendaEntity buildAgendaEntity() {
        return AgendaEntity.builder()
                           .title(AgendaServiceTest.MOCKED_TITLE)
                           .description(AgendaServiceTest.MOCKED_DESCRIPTION)
                           .build();
    }
}

package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.AssociateDTO;
import com.desafiovotacao.api.v1.dtos.responses.AssociateResponseDTO;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
import com.desafiovotacao.api.v1.exceptions.AssociateFoundException;
import com.desafiovotacao.api.v1.exceptions.AssociateNotFoundException;
import com.desafiovotacao.api.v1.mappers.AssociateMapper;
import com.desafiovotacao.api.v1.repositories.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociateService
{
    private final AssociateRepository associateRepository;
    
    public AssociateResponseDTO create(AssociateDTO associateDTO){
        String cpf = associateDTO.cpf();
        
        associateRepository.findByCpf(cpf)
                           .ifPresent(associate -> {
                               throw new AssociateFoundException();
                           });

        AssociateEntity entity = associateRepository.save(AssociateEntity.builder()
                                                                         .name(associateDTO.name())
                                                                         .cpf(associateDTO.cpf())
                                                                         .build());

        return AssociateMapper.toResponseDTO(entity);
    }

    public AssociateResponseDTO findById(Integer associateId) {
        AssociateEntity associate = associateRepository.findById(associateId)
                                                       .orElseThrow(() -> new AssociateNotFoundException());

        return AssociateMapper.toResponseDTO(associate);
    }

    public List<AssociateResponseDTO> listAll() {
        List<AssociateEntity> associates = associateRepository.findAll();

        return AssociateMapper.toResponseDTOList(associates);
    }
}

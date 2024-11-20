package com.desafiovotacao.api.v1.mappers;

import com.desafiovotacao.api.v1.dtos.responses.AssociateResponseDTO;
import com.desafiovotacao.api.v1.entities.AssociateEntity;

import java.util.List;

public class AssociateMapper {
    
    private AssociateMapper() {}
    
    public static AssociateResponseDTO toResponseDTO(AssociateEntity entity) {
        return new AssociateResponseDTO(entity.getId(),
                                        entity.getName(),
                                        entity.getCpf(),
                                        entity.getCreatedAt());
    }

    public static List<AssociateResponseDTO> toResponseDTOList(List<AssociateEntity> associates) {
        return associates.stream().map(AssociateMapper::toResponseDTO).toList();
    }
}

package com.desafiovotacao.api.v1.mappers;

import com.desafiovotacao.api.v1.dtos.responses.AssociateResponseDTO;
import com.desafiovotacao.api.v1.entities.AssociateEntity;

public class AssociateMapper {
    public static AssociateResponseDTO toResponseDTO(AssociateEntity entity) {
        return new AssociateResponseDTO(entity.getId(),
                                        entity.getName(),
                                        entity.getCpf(),
                                        entity.getCreatedAt());
    }
}

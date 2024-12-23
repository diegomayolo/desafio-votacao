package com.desafiovotacao.api.v1.mappers;

import com.desafiovotacao.api.v1.dtos.responses.VoteResponseDTO;
import com.desafiovotacao.api.v1.entities.VoteEntity;

public class VoteMapper {
    
    private VoteMapper() {}
    
    public static VoteResponseDTO toResponseDTO(VoteEntity entity) {
        return new VoteResponseDTO(entity.getId(),
                                   entity.getAssociate().getId(),
                                   entity.getAgenda().getId(),
                                   entity.getVote());
    }
}

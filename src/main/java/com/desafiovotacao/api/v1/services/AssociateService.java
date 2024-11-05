package com.desafiovotacao.api.v1.services;

import com.desafiovotacao.api.v1.dtos.AssociateDTO;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
import com.desafiovotacao.api.v1.exceptions.AssociateFoundException;
import com.desafiovotacao.api.v1.exceptions.AssociateNotFoundException;
import com.desafiovotacao.api.v1.exceptions.InvalidCpfException;
import com.desafiovotacao.api.v1.repositories.AssociateRepository;
import org.springframework.stereotype.Service;

import com.desafiovotacao.utils.CpfUtilities;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssociateService
{
    private final AssociateRepository associateRepository;
    
    public AssociateEntity create(AssociateDTO associateDTO){
        String cpf = associateDTO.cpf();
        
        associateRepository.findByCpf(cpf)
                           .ifPresent(associate -> {
                               throw new AssociateFoundException();
                           });
        
        if(!CpfUtilities.isValidCpf(cpf))
        {
            throw new InvalidCpfException();
        }

        AssociateEntity associate = AssociateEntity.builder()
                                                   .name(associateDTO.name())
                                                   .cpf(associateDTO.cpf())
                                                   .build();
        
        associateRepository.save(associate);
        
        return associate;
    }

    public AssociateEntity findById(Integer associateId) {
        return this.associateRepository.findById(associateId).orElseThrow(() -> new AssociateNotFoundException());
    }
}

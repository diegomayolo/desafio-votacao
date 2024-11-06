package com.desafiovotacao.api.v1.client.services;

import com.desafiovotacao.api.v1.client.dtos.FakeCPFDTO;
import com.desafiovotacao.api.v1.enums.VotingEligibilityEnum;
import com.desafiovotacao.utils.CpfUtilities;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FakeCPFService {
    public ResponseEntity<FakeCPFDTO> isValidCpf(String cpf) {
        boolean isCpfValid = CpfUtilities.randomValidCpf(cpf);

        if (!isCpfValid) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FakeCPFDTO(VotingEligibilityEnum.UNABLE_TO_VOTE));
        }

        boolean isCpfAbleToVote = CpfUtilities.randomValidCpf(cpf);

        if (isCpfAbleToVote) {
            return ResponseEntity.ok().body(new FakeCPFDTO(VotingEligibilityEnum.ABLE_TO_VOTE));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FakeCPFDTO(VotingEligibilityEnum.UNABLE_TO_VOTE));
    }
}
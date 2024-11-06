package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.VoteDTO;
import com.desafiovotacao.api.v1.dtos.responses.VoteResponseDTO;
import com.desafiovotacao.api.v1.services.VoteService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    
    @PostMapping
    @Counted(value = "votes.count", description = "Contagem total de votos")
    @Timed(value = "votes.timed", longTask = true, description = "Tempo de processamento para a contabilização do voto")
    public ResponseEntity<Object> create(@Valid @RequestBody VoteDTO voteDTO) {
        try {
            VoteResponseDTO responseDTO = voteService.create(voteDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

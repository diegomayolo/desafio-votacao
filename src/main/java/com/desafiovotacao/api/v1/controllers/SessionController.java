package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.SessionDTO;
import com.desafiovotacao.api.v1.dtos.responses.SessionResponseDTO;
import com.desafiovotacao.api.v1.services.SessionService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    
    @PostMapping
    @Counted(value = "session.count", description = "Contagem total de sessões")
    @Timed(value = "session.timed", longTask = true, description = "Tempo de processamento para o cadastro de uma sessão")
    public ResponseEntity<Object> create(@Valid @RequestBody SessionDTO sessionDTO) {
        try {
            SessionResponseDTO responseDTO = sessionService.create(sessionDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    
    @PostMapping
    @Counted(value = "session.count.create", description = "Contagem total de sessões")
    @Timed(value = "session.timed.create", longTask = true, description = "Tempo de processamento para o cadastro de uma sessão")
    public ResponseEntity<Object> create(@Valid @RequestBody SessionDTO sessionDTO) {
        try {
            SessionResponseDTO responseDTO = sessionService.create(sessionDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{sessionId}")
    @Counted(value = "session.count.get.by.id", description = "Contagem total de sessões obtidas por id")
    @Timed(value = "session.timed.get.by.id", longTask = true, description = "Tempo de processamento para obter uma sessão por id")
    public ResponseEntity<Object> findById(@PathVariable Integer sessionId) {
        try {
            SessionResponseDTO responseDTO = sessionService.findById(sessionId);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    @Counted(value = "session.count.get.all", description = "Contagem total de listagem de todas as sessões")
    @Timed(value = "session.timed.get.all", longTask = true, description = "Tempo de processamento para obter todas as sessões")
    public ResponseEntity<Object> listAll() {
        try {
            List<SessionResponseDTO> sessionResponseDTOS = sessionService.listAll();
            
            return ResponseEntity.ok().body(sessionResponseDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

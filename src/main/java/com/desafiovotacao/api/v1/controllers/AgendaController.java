package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AgendaDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.services.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agenda")
@RequiredArgsConstructor
public class AgendaController {
    private final AgendaService agendaService;
    
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody AgendaDTO agendaDTO) {
        try {
            AgendaResponseDTO responseDTO = agendaService.create(agendaDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/result/{agendaId}")
    public ResponseEntity<Object> result(@PathVariable Integer agendaId) {
        try {
            return ResponseEntity.ok().body(agendaService.getResult(agendaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

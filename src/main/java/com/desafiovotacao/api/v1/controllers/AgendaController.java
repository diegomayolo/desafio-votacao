package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AgendaDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResultResponseDTO;
import com.desafiovotacao.api.v1.services.AgendaService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agenda")
@RequiredArgsConstructor
public class AgendaController {
    private final AgendaService agendaService;
    
    @PostMapping
    @Counted(value = "agenda.count", description = "Contagem total de pautas")
    @Timed(value = "agenda.timed", longTask = true, description = "Tempo de processamento para o cadastro de uma pauta")
    public ResponseEntity<Object> create(@Valid @RequestBody AgendaDTO agendaDTO) {
        try {
            AgendaResponseDTO responseDTO = agendaService.create(agendaDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/result/{agendaId}")
    @Counted(value = "agenda.count.result", description = "Contagem total de buscas por resultados de pautas")
    @Timed(value = "agenda.timed.result", longTask = true, description = "Tempo de processamento para buscar o resultado de uma pauta")
    public ResponseEntity<Object> result(@PathVariable Integer agendaId) {
        try {
            AgendaResultResponseDTO responseDTO = agendaService.getResult(agendaId);
            
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{agendaId}")
    @Counted(value = "agenda.count.get.by.id", description = "Contagem total de buscas por pautas por id")
    @Timed(value = "agenda.timed.get.by.id", longTask = true, description = "Tempo de processamento para buscar uma pauta por id")
    public ResponseEntity<Object> findById(@PathVariable Integer agendaId) {
        try {
            AgendaResponseDTO responseDTO = agendaService.findById(agendaId);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    @Counted(value = "agenda.count.all", description = "Contagem total de buscas por todas as pautas")
    @Timed(value = "agenda.timed.all", longTask = true, description = "Tempo de processamento para buscar todas as pautas")
    public ResponseEntity<Object> listAll() {
        try {
            List<AgendaResponseDTO> responseDTOS = agendaService.listAll();
            
            return ResponseEntity.ok().body(responseDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AssociateDTO;
import com.desafiovotacao.api.v1.dtos.responses.AssociateResponseDTO;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.desafiovotacao.api.v1.services.AssociateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/associate")
@RequiredArgsConstructor
public class AssociateController
{
    private final AssociateService associateService;
    
    @PostMapping
    @Counted(value = "associate.count", description = "Contagem total de associados")
    @Timed(value = "associate.timed", longTask = true, description = "Tempo de processamento para o cadastro de um associado")
    public ResponseEntity<Object> create(@Valid @RequestBody AssociateDTO associateDTO)
    {
        try {
            AssociateResponseDTO responseDTO = associateService.create(associateDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Integer id) {
     
        try {
            AssociateEntity associate = associateService.findById(id);

            if (associate == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(associate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

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

import java.util.List;

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
    @Counted(value = "associate.count.get.by.id", description = "Contagem total de associados buscados por id")
    @Timed(value = "associate.timed.get.by.id", longTask = true, description = "Tempo de processamento para buscar um associado por id")
    public ResponseEntity<Object> findById(@PathVariable Integer id) {
     
        try {
            AssociateResponseDTO responseDTO = associateService.findById(id);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    @Counted(value = "associate.count.get.all", description = "Contagem total que todas os associados foram buscados")
    @Timed(value = "associate.timed.get.all", longTask = true, description = "Tempo de processamento para buscar todos os associados")
    public ResponseEntity<Object> listAll() {
        try {
            List<AssociateResponseDTO> responseDTOS = associateService.listAll();
            
            return ResponseEntity.ok(responseDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}

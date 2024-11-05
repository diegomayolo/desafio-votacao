package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AssociateDTO;
import com.desafiovotacao.api.v1.entities.AssociateEntity;
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
    public ResponseEntity<Object> create(@Valid @RequestBody AssociateDTO associateDTO)
    {
        try {
            AssociateEntity associateEntity = associateService.create(associateDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(associateEntity);
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
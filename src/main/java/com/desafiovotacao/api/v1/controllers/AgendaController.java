package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AgendaDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResponseDTO;
import com.desafiovotacao.api.v1.dtos.responses.AgendaResultResponseDTO;
import com.desafiovotacao.api.v1.services.AgendaService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Pautas",
        description = "Gerencia o ciclo de vida das pautas em assembleias, incluindo a criação, consulta, " +
                      "e obtenção de resultados de pautas, bem como a listagem de todas as pautas cadastradas."
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agenda")
public class AgendaController {
    private final AgendaService agendaService;
    
    @PostMapping
    @Operation(
            summary = "Cadastrar uma nova pauta",
            description = "Este endpoint cria uma nova pauta para uma assembleia."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgendaResponseDTO.class))
    )
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
    @Operation(
            summary = "Buscar resultado de uma pauta",
            description = "Este endpoint busca o resultado de uma pauta a partir do seu código identificador"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgendaResultResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro na requisição devido ao código identificador inexistente",
            content = @Content(mediaType = "text/plain",
            schema = @Schema(implementation = String.class),
            examples = @ExampleObject(value = "Pauta não encontrada"))
    )
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
    @Operation(
            summary = "Buscar pauta por código identificador",
            description = "Este endpoint busca uma pauta a partir do seu código identificador"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", 
                               schema = @Schema(implementation = AgendaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro na requisição devido ao código identificador inexistente",
            content = @Content(mediaType = "text/plain",
                               schema = @Schema(implementation = String.class),
                               examples = @ExampleObject(value = "Pauta não encontrada"))
    )
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
    @Operation(
            summary = "Listar todas as pautas",
            description = "Este endpoint busca todas as pautas cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                               schema = @Schema(implementation = List.class))
    )
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

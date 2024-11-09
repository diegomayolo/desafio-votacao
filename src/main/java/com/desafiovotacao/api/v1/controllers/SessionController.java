package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.SessionDTO;
import com.desafiovotacao.api.v1.dtos.responses.SessionResponseDTO;
import com.desafiovotacao.api.v1.services.SessionService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Cadastrar uma nova sessão",
            description = "Este endpoint cria uma nova sessão para uma pauta."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Sessão criada com sucesso",
            content = @Content(mediaType = "application/json", 
                               schema = @Schema(implementation = SessionResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro na requisição. Possíveis causas incluem:" +
                          "1. Pauta não encontrada." +
                          "2. Sessão ativa para esta pauta",
            content = @Content(mediaType = "text/plain", 
                               schema = @Schema(implementation = String.class), 
                               examples = @ExampleObject(value = "Pauta não encontrada \n" +
                                                                 "Sessão ativa para esta pauta"))
    )
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
    @Operation(
            summary = "Buscar sessão por código identificador",
            description = "Este endpoint busca uma sessão a partir do seu código identificador"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sessão encontrada",
            content = @Content(mediaType = "application/json", 
                               schema = @Schema(implementation = SessionResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro na requisição devido ao código identificador inexistente",
            content = @Content(mediaType = "text/plain", 
                               schema = @Schema(implementation = String.class),
                               examples = @ExampleObject(value = "Sessão não encontrada"))
    )
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
    @Operation(
            summary = "Listar todas as sessões",
            description = "Este endpoint busca todas as sessões cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sessões encontradas",
            content = @Content(mediaType = "application/json", 
                               schema = @Schema(implementation = List.class))
    )
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

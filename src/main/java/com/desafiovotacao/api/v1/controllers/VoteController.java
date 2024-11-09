package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.VoteDTO;
import com.desafiovotacao.api.v1.dtos.responses.VoteResponseDTO;
import com.desafiovotacao.api.v1.services.VoteService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Inserir um novo voto",
            description = "Este endpoint cria um novo voto para uma assembleia." +
                          "O voto pode ser positivo ou negativo." +
                          "O voto é vinculado a um associado e a uma pauta."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", 
                               schema = @Schema(implementation = VoteResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro na requisição. Possíveis causas incluem:\n " +
                          "1. Associado já registrou voto nesta pauta.\n " +
                          "2. A sessão de votação está fechada.\n " +
                          "3. Dados inválidos fornecidos na requisição (ex: associado ou pauta inexistente.",
            content = @Content(mediaType = "text/plain", 
                               schema = @Schema(implementation = String.class),
                               examples = @ExampleObject(value = "A sessão de votação está encerrada."))
    )
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

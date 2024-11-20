package com.desafiovotacao.api.v1.controllers;

import com.desafiovotacao.api.v1.dtos.AssociateDTO;
import com.desafiovotacao.api.v1.dtos.responses.AssociateResponseDTO;
import com.desafiovotacao.api.v1.services.AssociateService;
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
        name = "Associados", 
        description = "Gerencie e consulte informações dos associados para participação em votações."
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/associate")
public class AssociateController
{
    private final AssociateService associateService;
    
    @PostMapping
    @Operation(
            summary = "Cadastrar um novo associado",
            description = "Este enpoint cadastra um novo associado a partir de um nome e cpf"
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json",
                               schema = @Schema(implementation = AssociateResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "201",
            description = """
                          Erro na requisição. Possíveis causas incluem:
                          1. Associado já cadastrado.
                          2. Dados inválidos fornecidos na requisição (ex: nome ou cpf inválido.).
                          Verifique os dados fornecidos.
                          """,
            content = @Content(mediaType = "text/plain",
                               schema = @Schema(implementation = String.class),
                               examples = @ExampleObject(value = "Associado já cadastrado"))
    )
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
    @Operation(
            summary = "Buscar associado por código identificador",
            description = "Este endpoint busca um associado a partir de um código identificador"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                               schema = @Schema(implementation = AssociateResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro na requisição devido ao código identificador inexistente",
            content = @Content(mediaType = "text/plain",
                               schema = @Schema(implementation = String.class),
                               examples = @ExampleObject(value = "Associado não encontrado"))
    )
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
    @Operation(
            summary = "Listar todos os associados",
            description = "Este endpoint busca todos os associados cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                               schema = @Schema(implementation = List.class))
    )
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

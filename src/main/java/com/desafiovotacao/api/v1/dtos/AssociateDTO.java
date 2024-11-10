package com.desafiovotacao.api.v1.dtos;

import com.desafiovotacao.utils.validator.CpfOrCnpj;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AssociateDTO(
        @Schema(description = "Nome do associado", example = "Usuário Teste")
        @NotBlank(message = "O preenchimento do campo 'nome' é obrigatório")
        String name,

        @Schema(description = "Documento identificador do associado", example = "XXXXXXXXXXX")
        @NotBlank(message = "O preenchimento do campo 'cpf' é obrigatório")
        @CpfOrCnpj
        String cpf
) {}
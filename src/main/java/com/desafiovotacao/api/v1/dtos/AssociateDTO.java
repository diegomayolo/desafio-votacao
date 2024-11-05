package com.desafiovotacao.api.v1.dtos;

import jakarta.validation.constraints.NotBlank;

public record AssociateDTO(
    @NotBlank(message = "O preenchimento do campo 'nome' é obrigatório")
    String name,

    @NotBlank(message = "O preenchimento do campo 'cpf' é obrigatório")
    String cpf
) {}
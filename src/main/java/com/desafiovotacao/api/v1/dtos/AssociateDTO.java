package com.desafiovotacao.api.v1.dtos;

import com.desafiovotacao.utils.validator.CpfOrCnpj;
import jakarta.validation.constraints.NotBlank;

public record AssociateDTO(
    @NotBlank(message = "O preenchimento do campo 'nome' é obrigatório")
    String name,

    @CpfOrCnpj
    @NotBlank(message = "O preenchimento do campo 'cpf' é obrigatório")
    String cpf
) {}
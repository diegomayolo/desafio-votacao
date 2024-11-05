package com.desafiovotacao.api.v1.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociateDTO
{
    @NotBlank(message = "O preenchimento do campo 'nome' é obrigatório")
    private String name;
    
    @NotBlank(message = "O preenchimento do campo 'cpf' é obrigatório")
    private String cpf;
}
package com.desafiovotacao.api.v1.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDTO {
    @NotBlank(message = "O preenchimento do título é obrigatório")
    private String title;
    private String description;
}

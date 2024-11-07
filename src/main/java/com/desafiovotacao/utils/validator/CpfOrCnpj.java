package com.desafiovotacao.utils.validator;

import jakarta.validation.Constraint;

@Constraint(validatedBy = CpfOrCnpjValidator.class)
public @interface CpfOrCnpj {
    String message() default "O número informado não é válido. Informe um CPF ou CNPJ válido, sem pontos, traços ou barras.";
}
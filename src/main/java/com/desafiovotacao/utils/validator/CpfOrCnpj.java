package com.desafiovotacao.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfOrCnpjValidator.class)
public @interface CpfOrCnpj {
    String message() default "O número informado não é válido. Informe um CPF ou CNPJ válido, sem pontos, traços ou barras.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
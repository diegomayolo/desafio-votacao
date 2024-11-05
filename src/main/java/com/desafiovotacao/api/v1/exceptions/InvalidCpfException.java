package com.desafiovotacao.api.v1.exceptions;

public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException() {
        super("CPF inválido");
    }
}

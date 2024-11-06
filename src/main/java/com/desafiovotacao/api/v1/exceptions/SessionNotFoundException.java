package com.desafiovotacao.api.v1.exceptions;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException() {
        super("Sessão não encontrada");
    }
}

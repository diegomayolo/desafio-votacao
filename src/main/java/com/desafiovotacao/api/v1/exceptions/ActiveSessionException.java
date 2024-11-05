package com.desafiovotacao.api.v1.exceptions;

public class ActiveSessionException extends RuntimeException {
    public ActiveSessionException() {
        super("Já existe uma sessão ativa para esta pauta.");
    }
}

package com.desafiovotacao.api.v1.exceptions;

public class AgendaNotFoundException extends RuntimeException {
    public AgendaNotFoundException() {
        super("Pauta não encontrada");
    }
}

package com.desafiovotacao.api.v1.exceptions;

public class AgendaNotFound extends RuntimeException {
    public AgendaNotFound() {
        super("Pauta não encontrada");
    }
}

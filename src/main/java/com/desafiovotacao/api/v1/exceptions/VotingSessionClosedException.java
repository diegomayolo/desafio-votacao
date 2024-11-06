package com.desafiovotacao.api.v1.exceptions;

public class VotingSessionClosedException extends RuntimeException{
    public VotingSessionClosedException() {
        super("A sessão de votação está encerrada.");
    }
}

package com.desafiovotacao.api.v1.exceptions;

public class AlreadyVoteException extends RuntimeException {
    public AlreadyVoteException() {
        super("O associado já votou nesta pauta.");
    }
}

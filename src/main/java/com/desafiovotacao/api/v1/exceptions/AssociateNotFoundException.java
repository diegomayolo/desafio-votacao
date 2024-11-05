package com.desafiovotacao.api.v1.exceptions;

public class AssociateNotFoundException extends RuntimeException {
    public AssociateNotFoundException() {
        super("Associado não encontrado");
    }
}

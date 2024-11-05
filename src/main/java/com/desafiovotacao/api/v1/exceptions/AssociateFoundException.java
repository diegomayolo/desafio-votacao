package com.desafiovotacao.api.v1.exceptions;

public class AssociateFoundException extends RuntimeException {
    public AssociateFoundException() {
        super("Associado já cadastrado");
    }
}

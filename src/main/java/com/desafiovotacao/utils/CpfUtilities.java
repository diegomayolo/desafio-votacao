package com.desafiovotacao.utils;

import java.security.SecureRandom;

public class CpfUtilities
{
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    
    private CpfUtilities() {}
    
    public static boolean randomValidCpf(String cpf) {
        return SECURE_RANDOM.nextBoolean();
    }
}

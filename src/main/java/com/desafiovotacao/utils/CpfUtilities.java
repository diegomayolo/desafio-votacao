package com.desafiovotacao.utils;

import java.util.Random;

public class CpfUtilities
{
    public static boolean randomValidCpf(String cpf) {
        return new Random().nextBoolean();
    }
}

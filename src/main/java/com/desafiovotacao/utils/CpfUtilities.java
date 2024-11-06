package com.desafiovotacao.utils;

import java.util.Random;

public class CpfUtilities
{
    public static boolean randomValidCpf(String cpf) {
        return new Random().nextBoolean();
    }
    
    public static boolean isValidCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{3}\\/\\d{3}\\/\\d{3}\\s*\\d{2}")) {
            return false;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        int firstDigit = calculateDigit(cpf.substring(0, 9), 10);
        int secondDigit = calculateDigit(cpf.substring(0, 9) + firstDigit, 11);

        return cpf.equals(cpf.substring(0, 9) + firstDigit + secondDigit);
    }

    private static int calculateDigit(String base, int weight) {
        int sum = 0;
        for (int i = 0; i < base.length(); i++) {
            sum += Character.getNumericValue(base.charAt(i)) * weight--;
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}

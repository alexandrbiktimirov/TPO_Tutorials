package org.example.tutoria5.utils;

import java.math.BigInteger;

public class Conversion {
    public static BigInteger toDecimal(String value, int base) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < value.length(); i++) {
            int digit = charToDigit(value.charAt(i));

            if (digit >= base) {
                throw new IllegalArgumentException("Invalid numeric value for the given base");
            }

            result = result.multiply(BigInteger.valueOf(base)).add(BigInteger.valueOf(digit));
        }

        return result;
    }

    public static String fromDecimal(BigInteger number, int base) {
        if (number.equals(BigInteger.ZERO)) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        BigInteger bigInteger = BigInteger.valueOf(base);

        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divideAndRemainder = number.divideAndRemainder(bigInteger);
            result.insert(0, getDigits().charAt(divideAndRemainder[1].intValue()));
            number = divideAndRemainder[0];
        }

        return result.toString();
    }

    public static int charToDigit(char c) {
        String digits = getDigits();

        int result = digits.indexOf(c);

        if (result == -1) {
            throw new IllegalArgumentException("Invalid character in numeric value");
        }

        return digits.indexOf(c);
    }

    public static String getDigits() {
        return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁ";
    }
}

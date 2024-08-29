package org.example.utils;

import java.util.Random;

public class Number {
    private static final Random rand = new Random();

    private Number() {
    }

    public static int generateRandomNumber(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
}

package com.kmpc.algobe.user.util;

import java.util.Random;

public class RandomCodeUtil {
    public static String createCode() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        return String.valueOf(random.nextInt(899999) + 100000);
    }
}

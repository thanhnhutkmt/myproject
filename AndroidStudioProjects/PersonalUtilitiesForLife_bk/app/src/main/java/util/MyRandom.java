package util;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Nhut on 6/19/2016.
 */
public class MyRandom {
    private static final int LENGTH = 20;
    public static String randomizeString() {
        Random r = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            sb.append((char)(r.nextInt(26) + 97));
        }
        return sb.toString();
    }

    public static String randomizeNumberString() {

        return "";
    }
}

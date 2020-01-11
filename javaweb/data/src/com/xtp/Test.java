package com.xtp;

public class Test {
    public static void main(String[] ars) {
        for (int i = 1179395; i <= 1179395; i++) {
            for (int j = 19968; j <= 40869; j++) {
                for (int m = 19968; m <= 40869; m++) {
                    if (i == 31 * j + m) {
                        System.out.println((char) j + "" + (char) m+" hash: "+i);
                    }
                }
            }
        }

    }
}
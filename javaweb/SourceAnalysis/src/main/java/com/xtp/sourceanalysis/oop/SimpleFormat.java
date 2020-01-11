package com.xtp.sourceanalysis.oop;

import java.util.Arrays;
import java.util.Formatter;

public class SimpleFormat {
    public static void main(String[] args) {
        int x = 5;
        double y = 5.324667;
        System.out.printf("Row 1: [%d %f]\n", x, y);
        System.out.format("Row 1: [%d %f]\n", x, y);

        Formatter f = new Formatter(System.out);
        f.format("Row 1: [%d %f]\n", x, y);

        String str = String.format("Row 1: [%d %f]\n", x, y);
        System.out.println(str);

        Integer[][] a = {
                {1, 2, 3}, {4, 5, 6},
                {7, 8, 3}, {9, 10, 6}
        };
        System.out.println(Arrays.deepToString(a));

    }
}

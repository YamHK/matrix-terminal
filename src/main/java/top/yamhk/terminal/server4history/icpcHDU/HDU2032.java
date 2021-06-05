package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2032 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        Integer[][] s = new Integer[55][55];
        for (int i = 0; i < 55; i++) {
            s[i][1] = 1;
            s[i][i] = 1;
        }
        for (int i = 2; i < 55; i++) {
            for (int j = 2; j < i; j++) {
                s[i][j] = s[i - 1][j - 1] + s[i - 1][j];
            }
        }
        while (in.hasNext()) {
            n = in.nextInt();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= i; j++) {
                    System.out.print(s[i][j]);
                    if (i == j) {
                        System.out.println();
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
        in.close();
    }
}

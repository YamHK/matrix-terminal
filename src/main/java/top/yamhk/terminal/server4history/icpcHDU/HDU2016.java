package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2016 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Integer n, p, min, temp;

        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            Integer[] s = new Integer[n];
            min = 1111;
            p = 0;
            for (int i = 0; i < n; i++) {
                s[i] = in.nextInt();
                if (i == 0) {
                    p = i;
                    min = s[i];
                } else {
                    if (s[i] < min) {
                        p = i;
                        min = s[i];
                    }
                }
            }
            if (p != 0) {
                temp = s[0];
                s[0] = s[p];
                s[p] = temp;
            }
            for (int i = 0; i < n; i++) {
                if (i != 0) {
                    System.out.print(" ");
                }
                System.out.print(s[i]);
            }
            System.out.println();
        }
        in.close();
    }
}

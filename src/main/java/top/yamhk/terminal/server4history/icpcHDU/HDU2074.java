package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2074 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer t, count, num;
        String[] s;
        String[][] res;
        String n;
        String w;
        String print;
        count = 0;
        while (in.hasNext()) {
            s = in.nextLine().split(" ");
            t = Integer.valueOf(s[0]);
            res = new String[t + 1][t + 1];
            n = s[1];
            w = s[2];
            num = (t + 1) / 2;
            for (int i = 1; i <= num; i++) {
                print = (num - (i - 1)) % 2 == 0 ? w : n;
                for (int j = i; j <= t - (i - 1); j++) {
                    res[i][j] = print;// up
                    res[j][i] = print;// left
                    res[t - (i - 1)][t - (j - 1)] = print;// right
                    res[t - (j - 1)][t - (i - 1)] = print;// down
                }
                // for (int k = 0; k <= t; k++) {
                // for (int j = 0; j <= t; j++) {
                // System.out.print(null == res[k][j] ? "*" : res[k][j]);
                // }
                // System.out.println();
                // }
                // System.out.println("-----------------------" + i);
            }
            // print
            if (count++ != 0) {
                System.out.println();
            }
            if (t == 1) {
                System.out.print(n);
                System.out.println();
            } else if (t != 1 && t % 2 != 0) {
                for (int i = 1; i <= t; i++) {
                    for (int j = 1; j <= t; j++) {
                        if (i == 1 && j == 1 || i == t && j == t || i == 1
                                && j == t || i == t && j == 1) {
                            System.out.print(" ");
                        } else {
                            System.out.print(null == res[i][j] ? "*"
                                    : res[i][j].charAt(0));
                        }
                    }
                    System.out.println();
                }
            }

        }
        in.close();
    }
}

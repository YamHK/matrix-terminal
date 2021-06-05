package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2015 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer[] s = new Integer[101];
        Integer n, m, sum, count, res = -1;
        boolean flag;
        for (int i = 1; i < 101; i++) {
            s[i] = i * 2;
        }
        while (in.hasNext()) {
            n = in.nextInt();
            m = in.nextInt();
            sum = 0;
            count = 0;
            flag = false;
            for (int i = 1; i <= n; i++) {
                sum += s[i];
                if (i == n) {
                    if (i % m != 0) {
                        res = sum / (i % m);
                    } else {
                        res = sum / m;
                    }
                    flag = true;
                } else if (i % m == 0) {
                    res = sum / m;
                    flag = true;
                }
                if (flag) {
                    if (count != 0) {
                        System.out.print(" ");
                    }
                    System.out.print(res);
                    sum = 0;
                    flag = false;
                    count++;
                }
            }
            System.out.println();
        }
        in.close();
    }
}

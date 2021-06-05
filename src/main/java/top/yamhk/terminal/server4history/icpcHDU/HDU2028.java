package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2028 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, max, gcd, flag;
        Integer[] s;
        while (in.hasNext()) {
            n = in.nextInt();
            s = new Integer[n];
            max = flag = 0;
            for (int i = 0; i < n; i++) {
                s[i] = in.nextInt();
                if (i == 0 || s[i] > max) {
                    max = s[i];
                }
            }
            for (int i = 1; ; i++) {
                gcd = max * i;
                flag = 1;
                for (int j = 0; j < n; j++) {
                    if (gcd % s[j] != 0) {
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    System.out.println(gcd);
                    break;
                }
            }
        }
        in.close();
    }
}

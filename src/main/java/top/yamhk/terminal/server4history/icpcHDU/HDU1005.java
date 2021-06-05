package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU1005 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer a, b, n, i, flag = 100;
        Integer[] s = new Integer[1111];
        while (in.hasNext()) {
            a = in.nextInt();
            b = in.nextInt();
            n = in.nextInt();
            if (a == 0 && b == 0 && n == 0) {
                break;
            }
            s[1] = 1;
            s[2] = 1;
            for (i = 3; 3 * i < 1100; i++) {
                s[i] = (a * s[i - 1] + b * s[i - 2]) % 7;
                if (s[i] == s[2] && s[i - 1] == s[1]) {
                    flag = i - 2;
                    break;
                }
            }
            s[0] = s[flag];
            System.out.println(s[n % flag]);
        }
        in.close();
    }
}

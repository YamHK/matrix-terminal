package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2040 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, a, b, sum1, sum2;
        n = in.nextInt();
        while (n-- > 0) {
            a = in.nextInt();
            b = in.nextInt();
            sum1 = sum2 = 0;
            for (int i = 1; i < a; i++) {
                if (a % i == 0) {
                    sum1 += i;
                }
            }
            for (int i = 1; i < b; i++) {
                if (b % i == 0) {
                    sum2 += i;
                }
            }
            System.out.println((sum1 - b == 0 && sum2 - a == 0) ? "YES" : "NO");
        }
        in.close();
    }
}

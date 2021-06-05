package top.yamhk.terminal.server4history.icpcHDU;

import java.math.BigInteger;
import java.util.Scanner;

public class HDU2044 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, a, b;
        BigInteger[] s = new BigInteger[55];
        s[0] = new BigInteger("1");
        s[1] = new BigInteger("1");
        for (int i = 2; i < 50; i++) {
            s[i] = s[i - 1].add(s[i - 2]);
            // System.out.println(i + "," + s[i]);
        }
        n = in.nextInt();
        while (n-- > 0) {
            a = in.nextInt();
            b = in.nextInt();
            System.out.println(s[b - a]);

        }
        in.close();
    }
}

package top.yamhk.terminal.server4history.icpcHDU;

import java.math.BigInteger;
import java.util.Scanner;

public class HDU2070 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        BigInteger[] s = new BigInteger[55];
        s[0] = BigInteger.ZERO;
        s[1] = BigInteger.ONE;
        for (int i = 2; i < 55; i++) {
            s[i] = s[i - 1].add(s[i - 2]);
            // System.out.println(s[i]);
        }
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == -1) {
                break;
            }
            System.out.println(s[n]);
        }
        in.close();
    }
}

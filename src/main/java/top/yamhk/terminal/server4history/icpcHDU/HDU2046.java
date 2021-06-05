package top.yamhk.terminal.server4history.icpcHDU;

import java.math.BigInteger;
import java.util.Scanner;

public class HDU2046 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        BigInteger[] s = new BigInteger[55];
        s[1] = new BigInteger("1");
        s[2] = new BigInteger("2");
        for (int i = 3; i < 55; i++) {
            s[i] = s[i - 1].add(s[i - 2]);
            // System.out.println(i + "," + s[i]);
        }
        while (in.hasNext()) {
            n = in.nextInt();
            System.out.println(s[n]);
        }
        in.close();
    }
}

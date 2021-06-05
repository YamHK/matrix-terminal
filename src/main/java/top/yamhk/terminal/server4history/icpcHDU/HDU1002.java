package top.yamhk.terminal.server4history.icpcHDU;

import java.math.BigInteger;
import java.util.Scanner;

public class HDU1002 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BigInteger a, b;
        Integer n, count;
        n = in.nextInt();
        count = 1;
        while (count <= n) {
            if (count != 1) {
                System.out.println();
            }
            a = in.nextBigInteger();
            b = in.nextBigInteger();
            System.out.println("Case " + count + ":");
            System.out.println(a + " + " + b + " = " + a.add(b));
            count++;
        }
        in.close();
    }
}

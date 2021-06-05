package top.yamhk.terminal.server4history.icpcHDU;

import java.math.BigInteger;
import java.util.Scanner;

public class HDU2085 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        BigInteger[] a, b;
        a = new BigInteger[35];
        b = new BigInteger[35];

        a[0] = BigInteger.ONE;
        b[0] = BigInteger.ZERO;
        for (int i = 1; i < 35; i++) {
            a[i] = a[i - 1].multiply(new BigInteger("3")).add(
                    b[i - 1].multiply(new BigInteger("2")));
            b[i] = a[i - 1].multiply(new BigInteger("1")).add(
                    b[i - 1].multiply(new BigInteger("1")));
        }
//		for(n=0;n<35;n++){
//			System.out.println(a[n]+", "+b[n]);
//		}
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == -1) {
                break;
            }
            System.out.println(a[n] + ", " + b[n]);
        }
        in.close();
    }
}

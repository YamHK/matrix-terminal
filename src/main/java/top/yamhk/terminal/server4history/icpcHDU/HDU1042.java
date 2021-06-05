package top.yamhk.terminal.server4history.icpcHDU;

import java.math.BigInteger;
import java.util.Scanner;

public class HDU1042 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        BigInteger res;
        while (in.hasNext()) {
            n = in.nextInt();
            res = BigInteger.ONE;
            for (int i = 1; i <= n; i++) {
                res = res.multiply(new BigInteger(String.valueOf(i)));
            }
            System.out.println(res);
        }
        in.close();
    }
}

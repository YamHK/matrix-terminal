package top.yamhk.terminal.server4history.icpcHDU;

import java.math.BigDecimal;
import java.util.Scanner;

public class HDU2054 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BigDecimal a, b;
        boolean flag;
        while (in.hasNext()) {
            a = in.nextBigDecimal().stripTrailingZeros();
            b = in.nextBigDecimal().stripTrailingZeros();
            flag = false;
            // System.out.println(a + "," + b);
            if (a.equals(b)) {
                flag = true;
            } else {
                a = a.add(BigDecimal.ONE).stripTrailingZeros();
                b = b.add(BigDecimal.ONE).stripTrailingZeros();
                if (a.equals(b)) {
                    flag = true;
                } else {
                    a = a.add(BigDecimal.ONE).stripTrailingZeros();
                    b = b.add(BigDecimal.ONE).stripTrailingZeros();
                    if (a.equals(b)) {
                        flag = true;
                    }
                }
            }
            // System.out.println(a + "," + b);
            System.out.println(flag == true ? "YES" : "NO");
        }
        in.close();
    }
}
//System.out.println(a.compareTo(b) == 0 ? "YES" : "NO");

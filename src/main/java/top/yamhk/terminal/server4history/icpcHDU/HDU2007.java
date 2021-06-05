package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2007 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, m, sum1, sum2;
        while (in.hasNext()) {
            n = in.nextInt();
            m = in.nextInt();
            sum1 = 0;
            sum2 = 0;
            if (n > m) {
                int temp = n;
                n = m;
                m = temp;
            }
            for (int i = n; i <= m; i++) {
                if (i % 2 == 0) {
                    sum1 += i * i;
                } else {
                    sum2 += i * i * i;
                }
            }
            System.out.println(sum1 + " " + sum2);
        }
        in.close();
    }
}

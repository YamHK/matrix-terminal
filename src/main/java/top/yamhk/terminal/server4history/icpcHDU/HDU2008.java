package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2008 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, sum1, sum2, sum3;
        Double temp;
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            sum1 = 0;
            sum2 = 0;
            sum3 = 0;
            for (int i = 0; i < n; i++) {
                temp = in.nextDouble();
                if (temp < 0) {
                    sum1++;
                } else if (temp == 0) {
                    sum2++;
                } else {
                    sum3++;
                }
            }
            System.out.println(sum1 + " " + sum2 + " " + sum3);
        }
        in.close();
    }
}

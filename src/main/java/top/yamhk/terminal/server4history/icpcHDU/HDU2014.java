package top.yamhk.terminal.server4history.icpcHDU;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HDU2014 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.00");
        Integer n, sum, min, max, temp;
        while (in.hasNext()) {
            n = in.nextInt();
            min = 101;
            max = -1;
            sum = 0;
            for (int i = 0; i < n; i++) {
                temp = in.nextInt();
                sum += temp;
                if (temp < min) {
                    min = temp;
                }
                if (temp > max) {
                    max = temp;
                }
            }
            System.out.println(df.format((sum - min - max) * 1.0 / (n - 2)));
        }
        in.close();
    }

}

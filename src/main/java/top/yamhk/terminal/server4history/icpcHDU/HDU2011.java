package top.yamhk.terminal.server4history.icpcHDU;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HDU2011 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.00");
        Integer n, num;
        Double[] s = new Double[1000];
        s[0] = 0.0;
        Double flag = 1.0;
        for (int i = 1; i < 1000; i++) {
            s[i] = s[i - 1] + flag * 1.0 / i;
            flag *= -1;
        }
        n = in.nextInt();
        while (n-- > 0) {
            num = in.nextInt();
            System.out.println(df.format(s[num]));
        }
        in.close();
    }
}

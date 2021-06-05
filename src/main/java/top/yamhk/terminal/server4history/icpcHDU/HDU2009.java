package top.yamhk.terminal.server4history.icpcHDU;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HDU2009 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.00");
        Integer n, m;
        Double m1, sum;
        while (in.hasNext()) {
            m = in.nextInt();
            n = in.nextInt();
            m1 = m * 1.0;
            sum = 0.0;
            for (int i = 0; i < n; i++) {
                sum += m1;
                m1 = Math.sqrt(m1);
            }
            System.out.println(df.format(sum));
        }
        in.close();
    }

}

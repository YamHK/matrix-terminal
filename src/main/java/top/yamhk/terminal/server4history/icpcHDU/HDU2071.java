package top.yamhk.terminal.server4history.icpcHDU;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HDU2071 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.00");
        Integer n, t;
        Double max, temp;
        n = in.nextInt();
        while (n-- > 0) {
            t = in.nextInt();
            max = 0.0;
            while (t-- > 0) {
                temp = in.nextDouble();
                max = temp > max ? temp : max;
            }
            System.out.println(df.format(max));
        }
        in.close();
    }
}

package top.yamhk.terminal.server4history.icpcHDU;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HDU2002 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.000");
        double r, v;
        while (in.hasNext()) {
            r = in.nextDouble();
            v = 3.1415927 * r * 4.0 / 3 * r * r;
            System.out.println(df.format(v));
        }
        in.close();
    }
}

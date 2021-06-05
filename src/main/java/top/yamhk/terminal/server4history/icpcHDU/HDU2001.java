package top.yamhk.terminal.server4history.icpcHDU;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HDU2001 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Double x1, y1, x2, y2, res;
        DecimalFormat df = new DecimalFormat("0.00");
        while (in.hasNext()) {
            x1 = in.nextDouble();
            y1 = in.nextDouble();
            x2 = in.nextDouble();
            y2 = in.nextDouble();
            res = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
            System.out.println(df.format(Math.sqrt(res)));
        }
        in.close();
    }
}

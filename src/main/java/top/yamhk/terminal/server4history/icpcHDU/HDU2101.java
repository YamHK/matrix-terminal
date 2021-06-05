package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2101 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // DecimalFormat df = new DecimalFormat("0.00");
        double a, b;
        while (in.hasNext()) {
            a = in.nextDouble();
            b = in.nextDouble();
            System.out.println((a + b) % 86 == 0 ? "yes" : "no");
        }
        in.close();
    }
}

package top.yamhk.terminal.server4history.icpcHDU;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HDU2099 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("00");
        Integer a, b, count;
        while (in.hasNext()) {
            a = in.nextInt();
            b = in.nextInt();
            if (a == 0 && b == 0) {
                break;
            }
            count = 0;
            for (int i = 0; i < 100; i++) {
                if ((a * 100 + i) % b == 0) {
                    if (count != 0) {
                        System.out.print(" ");
                    }
                    System.out.print(df.format(i));
                    count++;
                }
            }
            System.out.println();
        }
        in.close();
    }
}

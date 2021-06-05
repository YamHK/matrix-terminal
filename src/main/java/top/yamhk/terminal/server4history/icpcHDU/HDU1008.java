package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU1008 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, sum, level, x;
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            sum = 0;
            level = 0;
            for (int i = 0; i < n; i++) {
                x = in.nextInt();
                if (x > level) {
                    sum += (x - level) * 6 + 5;
                } else {
                    sum += (level - x) * 4 + 5;
                }
                level = x;
            }
            System.out.println(sum);
        }
        in.close();
    }
}

package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU1096 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer ca, n, x, sum;
        ca = in.nextInt();
        while (ca-- > 0) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            sum = 0;
            while (n-- > 0) {
                x = in.nextInt();
                sum = sum + x;
            }
            System.out.println(sum);
            if (ca > 0) {
                System.out.println();
            }
        }
        in.close();
    }
}

package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU1094 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, x, sum;
        while (in.hasNext()) {
            n = in.nextInt();
            sum = 0;
            while (n-- > 0) {
                x = in.nextInt();
                sum = sum + x;
            }
            System.out.println(sum);
            //System.out.println();
        }
        in.close();
    }
}

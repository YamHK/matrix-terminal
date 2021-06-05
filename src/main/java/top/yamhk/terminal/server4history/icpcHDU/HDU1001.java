package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU1001 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, sum;
        while (in.hasNext()) {
            sum = 0;
            n = in.nextInt();
            for (int i = 1; i <= n; i++) {
                sum += i;
            }
            System.out.println(sum);
            System.out.println();
        }
        in.close();
    }

}

package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2042 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, sum, day;
        n = in.nextInt();
        while (n-- > 0) {
            sum = 3;
            day = in.nextInt();
            for (int i = 0; i < day; i++) {
                sum = (sum - 1) * 2;
            }
            System.out.println(sum);
        }
        in.close();
    }
}

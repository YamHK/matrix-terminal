package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2053 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, num;
        while (in.hasNext()) {
            n = in.nextInt();
            num = 0;
            for (int i = 1; i <= n; i++) {
                if (n % i == 0) {
                    num++;
                }
            }
            System.out.println(num % 2);
        }
        in.close();
    }
}

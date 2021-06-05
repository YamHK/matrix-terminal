package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2021 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, temp, sum;
        Integer[] s = {100, 50, 10, 5, 2, 1};
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            sum = 0;
            while (n-- > 0) {
                temp = in.nextInt();
                for (int i = 0; i < 6; i++) {
                    sum += temp / s[i];
                    temp = temp % s[i];
                }
            }
            System.out.println(sum);
        }
        in.close();
    }
}

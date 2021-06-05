package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2035 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, m, temp;
        while (in.hasNext()) {
            n = in.nextInt();
            m = in.nextInt();
            if (n == 0 && m == 0) {
                break;
            }
            temp = n % 1000;
            for (int i = 1; i < m; i++) {
                temp = temp * n % 1000;
            }
            System.out.println(temp);
        }
        in.close();
    }
}

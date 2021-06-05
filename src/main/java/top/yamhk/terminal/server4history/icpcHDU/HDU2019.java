package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2019 {
    public static void main(String[] args) {
        new HDU2019().work();
    }

    public void work() {
        Scanner in = new Scanner(System.in);

        Integer n, m;
        Integer[] s = new Integer[111];

        while (in.hasNext()) {
            n = in.nextInt();
            m = in.nextInt();
            if (n == 0 && m == 0) {
                break;
            }
            for (int i = 0; i < n; i++) {
                s[i] = in.nextInt();
            }
            boolean flag = true;
            for (int i = 0; i < n; i++) {
                if (i != 0) {
                    System.out.print(" ");
                }
                if (s[i] > m && flag) {
                    System.out.print(m + " ");
                    flag = false;
                }
                System.out.print(s[i]);
            }
            if (flag == true) {
                System.out.print(" " + m);
            }
            System.out.println();
        }
        in.close();
    }
}

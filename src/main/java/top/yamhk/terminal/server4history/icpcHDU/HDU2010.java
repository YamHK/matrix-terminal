package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2010 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer m, n, count, a, b, c;
        boolean[] s = new boolean[1001];
        for (int i = 100; i < 1000; i++) {
            a = i / 100;
            b = i / 10 % 10;
            c = i % 10;
            s[i] = a * a * a + b * b * b + c * c * c == i;
        }
        while (in.hasNext()) {
            m = in.nextInt();
            n = in.nextInt();
            count = 0;
            for (int i = m; i <= n; i++) {
                if (s[i] == true) {
                    if (count != 0) {
                        System.out.print(" ");
                    }
                    System.out.print(i);
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("no");
            } else {
                System.out.println();
            }
        }
        in.close();
    }

}

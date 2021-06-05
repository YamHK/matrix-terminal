package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2051 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer N, R, flag, count;
        Integer[] s;
        String[] st = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F".split(",");
        while (in.hasNext()) {
            N = in.nextInt();
            R = 2;
            flag = count = 0;
            s = new Integer[55];
            if (N < 0) {
                flag = 1;
                N *= -1;
            }
            while (N != 0) {
                s[count] = N % R;
                N /= R;
                count++;
            }
            if (flag == 1) {
                System.out.print("-");
            }
            for (int i = 0; i < count; i++) {
                System.out.print(st[s[count - i - 1]]);
            }
            System.out.println();
        }
        in.close();
    }
}

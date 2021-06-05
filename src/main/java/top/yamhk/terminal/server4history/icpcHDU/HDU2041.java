package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2041 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        Integer[] s = new Integer[55];
        s[1] = 1;
        s[2] = 1;
        s[3] = 2;
        for (int i = 4; i < 55; i++) {
            s[i] = s[i - 1] + s[i - 2];
        }
        n = in.nextInt();
        while (n-- > 0) {
            System.out.println(s[in.nextInt()]);
        }
        in.close();
    }
}

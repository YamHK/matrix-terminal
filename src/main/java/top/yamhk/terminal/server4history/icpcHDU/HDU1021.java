package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU1021 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        // Integer[] s = new Integer[1111];
        String[] ss = {"no", "no", "yes", "no"};
        // s[0] = 7;
        // s[1] = 11;
        // for (i = 2; 3 * i < 111; i++) {
        // s[i] = (s[i - 1] + s[i - 2]);
        // System.out.println(i + "---" + s[i] + "---"
        // + (s[i] % 3 == 0 ? "yes" : "no"));
        // }
        while (in.hasNext()) {
            n = in.nextInt();
            System.out.println(ss[n % 4]);
        }
        in.close();
    }
}

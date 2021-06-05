package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2055 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, va, vb;
        String a, b;
        String[] s;
        // System.out.println((int) 'A');
        // System.out.println((int) 'a');
        n = in.nextInt();
        in.nextLine();
        while (n-- > 0) {
            s = in.nextLine().split(" ");
            a = s[0];
            b = s[1];
            va = a.charAt(0) >= 'A' && a.charAt(0) <= 'Z' ? (int) a.charAt(0) - 64
                    : ((int) a.charAt(0) - 96) * -1;
            vb = Integer.valueOf(b);
            System.out.println(va + vb);
        }
        in.close();
    }
}

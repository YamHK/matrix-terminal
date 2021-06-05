package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2033 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer Case, count = 1;
        Integer[] s = new Integer[6];
        Integer a, b, c;
        Case = in.nextInt();
        while (count <= Case) {
            for (int i = 0; i < 6; i++) {
                s[i] = in.nextInt();
            }
            a = s[0] + s[3];
            b = s[1] + s[4];
            c = s[2] + s[5];
            if (c >= 60) {
                b += c / 60;
                c %= 60;
            }
            if (b >= 60) {
                a += b / 60;
                b %= 60;
            }
            System.out.println(a + " " + b + " " + c);
            count++;
        }
        in.close();
    }
}

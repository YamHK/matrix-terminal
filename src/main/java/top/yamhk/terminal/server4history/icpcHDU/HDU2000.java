package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2000 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = "";
        char a, b, c, t;
        while (in.hasNext()) {
            str = in.nextLine();
            a = str.charAt(0);
            b = str.charAt(1);
            c = str.charAt(2);
            if (a > b) {
                t = a;
                a = b;
                b = t;
            }
            if (a > c) {
                t = a;
                a = c;
                c = t;
            }
            if (b > c) {
                t = b;
                b = c;
                c = t;
            }
            System.out.println(a + " " + b + " " + c);
        }
        in.close();
    }
}

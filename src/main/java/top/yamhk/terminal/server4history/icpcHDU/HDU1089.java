package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU1089 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer a, b;
        while (in.hasNext()) {
            a = in.nextInt();
            b = in.nextInt();
            System.out.println(a + b);
        }
        in.close();
    }
}

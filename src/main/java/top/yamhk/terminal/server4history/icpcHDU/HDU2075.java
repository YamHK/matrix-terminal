package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2075 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, a, b;
        n = in.nextInt();
        while (n-- > 0) {
            a = in.nextInt();
            b = in.nextInt();
            System.out.println(a % b == 0 ? "YES" : "NO");
        }
        in.close();
    }
}

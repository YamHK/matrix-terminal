package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2039 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        Double a, b, c;
        n = in.nextInt();
        while (n-- > 0) {
            a = in.nextDouble();
            b = in.nextDouble();
            c = in.nextDouble();
            System.out.println((a + b > c && a + c > b && b + c > a) ? "YES"
                    : "NO");
        }
        in.close();
    }
}

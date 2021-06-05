package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

//小明A+B
public class HDU2096 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer Case, count = 0, a, b;
        Case = in.nextInt();
        while (count < Case) {
            a = in.nextInt() % 100;
            b = in.nextInt() % 100;
            System.out.println((a + b) % 100);
            count++;
        }
        in.close();
    }
}

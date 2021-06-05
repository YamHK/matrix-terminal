package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2081 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        n = in.nextInt();
        in.nextLine();
        while (n-- > 0) {
            System.out.println("6" + in.nextLine().substring(11 - 5));
        }
        in.close();
    }
}

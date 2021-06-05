package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2017 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, count;
        String str;
        n = in.nextInt();
        in.nextLine();
        while (n-- > 0) {
            str = in.nextLine();
            count = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                    count++;
                }
            }
            System.out.println(count);
        }
        in.close();
    }
}

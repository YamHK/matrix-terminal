package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2029 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, slen, flag;
        String str;
        n = in.nextInt();
        in.nextLine();
        while (n-- > 0) {
            str = in.nextLine();
            slen = str.length();
            flag = 1;
            for (int i = 0; i < slen / 2 && flag == 1; i++) {
                if (str.charAt(i) != str.charAt(slen - i - 1)) {
                    flag = 0;
                }
            }
            System.out.println(flag == 1 ? "yes" : "no");
        }
        in.close();
    }
}

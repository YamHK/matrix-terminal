package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2024 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, flag;
        String str;
        n = in.nextInt();
        in.nextLine();
        while (n-- > 0) {
            str = in.nextLine();
            flag = 1;
            if (!(str.charAt(0) >= 'a' && str.charAt(0) <= 'z'
                    || str.charAt(0) >= 'A' && str.charAt(0) <= 'Z' || str
                    .charAt(0) == '_')) {
                flag = 0;
            }
            for (int i = 0; i < str.length() && flag == 1; i++) {
                if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z'
                        || str.charAt(i) >= 'A' && str.charAt(i) <= 'Z'
                        || str.charAt(i) >= '0' && str.charAt(i) <= '9'
                        || str.charAt(i) == '_') {
                    flag = 1;
                } else {
                    flag = 0;
                }
            }
            System.out.println(flag == 1 ? "yes" : "no");
        }
        in.close();
    }
}

package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2043 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, slen, flag, f1, f2, f3, f4;
        String str;
        String ts = "~,!,@,#,$,%,^";
        n = in.nextInt();
        in.nextLine();
        while (n-- > 0) {
            str = in.nextLine();
            slen = str.length();
            flag = 0;
            if (slen >= 8 && slen <= 16) {
                flag = 1;
            }
            f1 = f2 = f3 = f4 = 0;
            for (int i = 0; i < slen; i++) {
                if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
                    f1 = 1;
                }
                if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                    f2 = 1;
                }
                if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                    f3 = 1;
                }
                if (ts.indexOf(str.charAt(i)) != -1) {
                    f4 = 1;
                }
            }
            if (f1 + f2 + f3 + f4 >= 3) {
                flag += 1;
            }
            // System.out.println(f1 + "," + f2 + "," + f3 + "," + f4);
            System.out.println(flag == 2 ? "YES" : "NO");
        }
        in.close();
    }
}

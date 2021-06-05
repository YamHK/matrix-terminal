package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2095TEL {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, res;
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            res = 0;
            for (int i = 0; i < n; i++) {
                res ^= in.nextInt();
            }
            System.out.println(res);
        }
        in.close();
    }
}

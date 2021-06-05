package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2012 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer x, y, z;
        boolean flag;
        String res;
        while (in.hasNext()) {
            flag = true;
            x = in.nextInt();
            y = in.nextInt();
            if (x == 0 && y == 0) {
                break;
            }
            for (int i = x; i <= y && flag == true; i++) {
                z = i * i + i + 41;
                for (int j = 2; j <= Math.sqrt(z) && flag == true; j++) {
                    if (z % j == 0) {
                        flag = false;
                    }
                }
            }
            res = flag ? "OK" : "Sorry";
            System.out.println(res);
        }
        in.close();
    }
}

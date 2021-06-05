package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2089 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, m;
        Integer[] s = new Integer[1001111];
        s[0] = 0;
        for (int i = 1; i < 1001111; i++) {
            s[i] = (String.valueOf(i).indexOf("4") == -1 && String.valueOf(i)
                    .indexOf("62") == -1) ? s[i - 1] + 1 : s[i - 1];
            //if(i<100)System.out.println(i+","+s[i]);
        }
        while (in.hasNext()) {
            n = in.nextInt();
            m = in.nextInt();
            if (n == 0 && m == 0) {
                break;
            }
            System.out.println(s[m] - s[n - 1]);
        }
        in.close();
    }
}

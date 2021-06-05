package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2005 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] s = new String[5];
        Integer sum;
        Integer[] month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        while (in.hasNext()) {
            s = in.nextLine().split("/");
            Integer y = Integer.valueOf(s[0]);
            Integer m = Integer.valueOf(s[1]);
            Integer d = Integer.valueOf(s[2]);
            sum = d;
            for (int i = 0; i < m - 1; i++) {
                sum += month[i];
            }
            if (y % 4 == 0 && y % 100 != 0 || y % 100 == 0 && y % 400 == 0) {
                if (m > 2) {
                    sum += 1;
                }
            }

            System.out.println(sum);
        }
        in.close();
    }
}

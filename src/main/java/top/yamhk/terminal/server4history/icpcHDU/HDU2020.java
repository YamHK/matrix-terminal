package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2020 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer num, temp;
        Integer[] s = new Integer[111];
        while (in.hasNext()) {
            num = in.nextInt();
            if (num == 0) {
                break;
            }
            for (int i = 0; i < num; i++) {
                s[i] = in.nextInt();
            }
            //选择排序
            for (int i = 0; i < num - 1; i++) {
                int p = i, max = Math.abs(s[i]);
                for (int j = i + 1; j < num; j++) {
                    if (Math.abs(s[j]) > max) {
                        p = j;
                        max = Math.abs(s[j]);
                    }
                }
                if (p != i) {
                    temp = s[i];
                    s[i] = s[p];
                    s[p] = temp;
                }
            }
            for (int i = 0; i < num; i++) {
                if (i != 0) {
                    System.out.print(" ");
                }
                System.out.print(s[i]);
            }
            System.out.println();
        }
        in.close();
    }
}

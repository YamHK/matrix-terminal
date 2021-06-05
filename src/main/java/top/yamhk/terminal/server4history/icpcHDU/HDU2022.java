package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2022 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // DecimalFormat df = new DecimalFormat("0.00");
        Integer m, n, max, flag;
        Integer[][] s;
        while (in.hasNext()) {
            m = in.nextInt();
            n = in.nextInt();
            // if (n == 0 && m == 0)break;
            s = new Integer[m][n];
            // 读取数据
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    s[i][j] = in.nextInt();
                }
            }
            max = Math.abs(s[0][0]);
            // 挑选目标
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (Math.abs(s[i][j]) > max) {
                        max = Math.abs(s[i][j]);
                    }
                }
            }
            // 回头找坐标
            flag = 0;
            for (int i = 0; i < m && flag == 0; i++) {
                for (int j = 0; j < n && flag == 0; j++) {
                    if (Math.abs(s[i][j]) == max) {
                        System.out.println((i + 1) + " " + (j + 1) + " " + s[i][j]);
                        flag = 1;
                    }
                }
            }
        }
        in.close();
    }

}

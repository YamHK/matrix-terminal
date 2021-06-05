package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2023 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //DecimalFormat df = new DecimalFormat("0.00");
        Integer n, m, count, flag;
        Integer[][] s;
        double[] avg1;
        double[] avg2;
        while (in.hasNext()) {
            n = in.nextInt();
            m = in.nextInt();
            //if (n == 0 && m == 0)break;
            count = 0;
            s = new Integer[n][m];
            avg1 = new double[n];
            avg2 = new double[m];
            //读取数据
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    s[i][j] = in.nextInt();
                }
            }
            //学生平均成绩
            for (int i = 0; i < n; i++) {
                avg1[i] = 0;
                for (int j = 0; j < m; j++) {
                    avg1[i] += s[i][j];
                }
                avg1[i] = avg1[i] * 1.0 / m;
            }
            //课程平均成绩
            for (int j = 0; j < m; j++) {
                avg2[j] = 0;
                for (int i = 0; i < n; i++) {
                    avg2[j] += s[i][j];
                }
                avg2[j] = avg2[j] * 1.0 / n;
            }
            // 挑选目标学生
            for (int i = 0; i < n; i++) {
                flag = 1;
                for (int j = 0; j < m && flag == 1; j++) {
                    if (s[i][j] >= avg2[j]) {
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                }
                if (flag == 1) {
                    count += 1;
                }
            }
            for (int i = 0; i < n; i++) {
                if (i != 0) {
                    System.out.print(" ");
                }
                //System.out.print(df.format(avg1[i]));
                System.out.printf("%.2f", avg1[i]);
            }
            System.out.println();
            for (int i = 0; i < m; i++) {
                if (i != 0) {
                    System.out.print(" ");
                }
                //System.out.print(df.format(avg2[i]));
                System.out.printf("%.2f", avg2[i]);
            }
            System.out.println();
            System.out.println(count);
            System.out.println();
        }
        in.close();
    }
}

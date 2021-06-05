package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2091 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] s;
        String p;
        Integer n, count;
        count = 0;
        while (in.hasNext()) {
            s = in.nextLine().split(" ");
            if (s[0].startsWith("@")) {
                break;//程序结束标记
            }
            p = s[0];//绘图字符
            n = Integer.valueOf(s[1]);//图形(等腰三角形)高度
            //Integer d=2*n-1;
            if (count++ != 0) {
                System.out.println();
            }
            for (int i = 1; i <= n; i++) {//行
                for (int j = 1; j <= n + i - 1; j++) {//列--n+i+1
                    System.out.print((j == n + (i - 1)) || j == n - (i - 1) || i == n ? p : " ");//右边+左边+最后一行
                }
                System.out.println();
            }
        }
        in.close();
    }
}

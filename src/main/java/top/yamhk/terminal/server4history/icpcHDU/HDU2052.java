package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2052 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, m;
        String[][] s;
        while (in.hasNext()) {
            m = in.nextInt();
            n = in.nextInt();
            s = new String[n + 2][m + 2];
            for (int i = 0; i < m + 2; i++) {
                s[0][i] = (i == 0 || i == m + 1) ? "+" : "-";
                s[n + 1][i] = (i == 0 || i == m + 1) ? "+" : "-";
            }
            for (int i = 0; i < n + 2; i++) {
                s[i][0] = (i == 0 || i == n + 1) ? "+" : "|";
                s[i][m + 1] = (i == 0 || i == n + 1) ? "+" : "|";
            }
            for (int i = 0; i < n + 2; i++) {
                for (int j = 0; j < m + 2; j++) {
                    System.out.print(null == s[i][j] ? " " : s[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
        in.close();
    }
}

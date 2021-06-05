package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2098 {
    // 素数判断
    private static boolean primeJudge(Integer N) {
        boolean flag = true;
        for (int j = 2; j * j <= N && flag == true; j++) {
            flag = N % j != 0;
        }
        return flag;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, count;
        boolean[] prime = new boolean[10001];
        prime[2] = true;// 特判,
        for (int i = 3; i < 10001; i++) {
            prime[i] = primeJudge(i) == true;
        }
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            count = 0;
            for (int i = 2; i * 2 < n; i++) {
                if (prime[i] && prime[n - i]) {
                    count++;
                }
            }
            System.out.println(count);
        }
        in.close();
    }
}

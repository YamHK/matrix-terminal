package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2097 {
    private static Integer change(Integer N, Integer R) {
        Integer[] s = new Integer[55];
        int count = 0;
        while (N != 0) {
            s[count] = N % R;
            N /= R;
            count++;
        }
        Integer sum = 0;
        for (int i = 0; i < count; i++) {
            sum += s[count - i - 1];
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, a, b, c;
        String str_a, str_b;
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            a = change(n, 10);
            b = change(n, 12);
            c = change(n, 16);

            str_a = n + " is a Sky Number.";
            str_b = n + " is not a Sky Number.";
            System.out.println(a.equals(b) && a.equals(c) ? str_a : str_b);
        }
        in.close();
    }
}

package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU1003WA {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Integer s, e, num, sum, max, maxSum;
        Integer Case, count;
        Case = in.nextInt();
        count = 1;
        while (count <= Case) {
            if (count != 1) {
                System.out.println();
            }
            num = in.nextInt();
            s = 1;
            e = 1;
            sum = 0;
            max = -1111;
            maxSum = -1111;
            Integer temp;
            for (int i = 1; i <= num; i++) {
                temp = in.nextInt();
                max = temp > max ? temp : max;
                if (temp > sum) {
                    s = i;
                    e = i;
                    maxSum = temp;
                    sum = temp;
                    continue;
                }
                sum += temp;
                if (sum > maxSum) {
                    maxSum = sum;
                    e = i;
                }
            }
            System.out.println("Case " + count + ":");
            System.out.println(maxSum + " " + s + " " + e);
            count++;
        }
        in.close();
    }
}

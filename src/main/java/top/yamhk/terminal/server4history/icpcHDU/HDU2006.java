package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2006 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, temp, sum;
        while (in.hasNext()) {
            n = in.nextInt();
            sum = 1;
            while (n-- > 0) {
                temp = in.nextInt();
                sum *= temp % 2 == 0 ? 1 : temp;
            }
            System.out.println(sum);
        }
        in.close();
    }
}

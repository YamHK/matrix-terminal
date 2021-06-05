package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2027 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // DecimalFormat df = new DecimalFormat("0.00");
        Integer n, num1, num2, num3, num4, num5;
        String str;
        char ch;
        n = in.nextInt();
        in.nextLine();
        while (n-- > 0) {
            str = in.nextLine();
            num1 = num2 = num3 = num4 = num5 = 0;
            // if (n == 0 && m == 0)break;0
            for (int i = 0; i < str.length(); i++) {
                ch = str.charAt(i);
                if (ch == 'a') {
                    num1++;
                }
                if (ch == 'e') {
                    num2++;
                }
                if (ch == 'i') {
                    num3++;
                }
                if (ch == 'o') {
                    num4++;
                }
                if (ch == 'u') {
                    num5++;
                }
            }
            System.out.println("a:" + num1);
            System.out.println("e:" + num2);
            System.out.println("i:" + num3);
            System.out.println("o:" + num4);
            System.out.println("u:" + num5);
            if (n != 0) {
                System.out.println();
            }
        }
        in.close();
    }

}

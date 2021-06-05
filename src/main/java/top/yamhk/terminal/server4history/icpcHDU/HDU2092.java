package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2092 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer a, b, flag;
        double d;
        while (in.hasNext()) {
            a = in.nextInt();
            b = in.nextInt();
            if (a == 0 && b == 0) {
                break;
            }
            /*
             * x=(-b±√△)/2a	根为有理数，即可表示成整数之商 	-b,2a均为整数，√△必为至少，即△为完全平方数
             * 有解且为整数 x+y=a; x*y=b; x*(a-x)=b-->x*x-ax=b=0-->(-a)*(-a)-4*a*b>=0
             *
             * System.out.println("x*x-(" + a + ")x+(" + b + ")=0");
             */
            d = a * a - 4 * b;
            flag = d - (int) Math.sqrt(d) * (int) Math.sqrt(d) == 0 ? 1 : 0;
            System.out.println(d >= 0 && flag == 1 ? "Yes" : "No");
        }
        in.close();
    }
}

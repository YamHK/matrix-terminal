package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2018 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n;
        Integer[] s = new Integer[66];
        s[0] = 0;
        for (int i = 1; i < 66; i++) {
            s[i] = 0;
            s[i] += s[i - 1];
            if (i - 3 > 0) {
                s[i] += s[i - 3];
            } else {
                s[i] += 1;
            }
        }
//		for(int i=1;i<25;i++){
//			System.out.println("第"+i+"年:--->"+s[i]+"头母牛");
//		}
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            System.out.println(s[n]);
        }
        in.close();
    }
}

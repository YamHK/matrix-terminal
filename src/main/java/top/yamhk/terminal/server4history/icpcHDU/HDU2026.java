package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2026 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str;
        while (in.hasNext()) {
            str = in.nextLine();
            for (int i = 0; i < str.length(); i++) {
                //System.out.println(str.charAt(i));
                if (i == 0 || " ".equals(str.charAt(i - 1) + "")) {
                    System.out.print((char) ((int) str.charAt(i) - 32));
                } else {
                    System.out.print(str.charAt(i));
                }
            }
            System.out.println();
        }
        in.close();
    }
}

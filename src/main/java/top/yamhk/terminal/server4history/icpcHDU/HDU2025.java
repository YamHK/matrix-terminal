package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2025 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char max, temp;
        String str;
        while (in.hasNext()) {
            str = in.nextLine();
            max = str.charAt(0);
            for (int i = 0; i < str.length(); i++) {
                temp = str.charAt(i);
                if (temp > max) {
                    max = temp;
                }
            }
            // output
            for (int i = 0; i < str.length(); i++) {
                temp = str.charAt(i);
                System.out.print(temp);
                if (temp == max) {
                    System.out.print("(max)");
                }
            }
            System.out.println();
        }
        in.close();
    }
}

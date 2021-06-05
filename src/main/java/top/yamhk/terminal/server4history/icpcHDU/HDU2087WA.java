package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2087WA {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer ca, count;
        String str, st1, st2;
        ca = 0;
        while (in.hasNext()) {
            str = in.nextLine();
            if (str.indexOf("#") != -1) {
                break;
            }
            if (ca++ != 0) {
                System.out.println();
            }
            count = 1;
            // System.out.println(str.split(" ").length);
            st1 = str.split(" ")[0];
            st2 = str.split(" ")[1];
            // for(int i=0;i<str.split(" ").length;i++){
            // System.out.println("-------#" + str.split(" ")[i] + "#");
            // }

            while (st2.length() < 1) {
                //System.out.println(count + "," + str.length());
                st2 = str.split(" ")[count++];
            }

            count = 0;
            while (st1.indexOf(st2) != -1) {
                count++;
                st1 = st1.substring(st1.indexOf(st2) + st2.length());
                // System.out.println(st1 + "---" + st2);
            }
            System.out.println(count);
        }
        in.close();
    }
}

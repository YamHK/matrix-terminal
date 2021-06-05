package top.yamhk.terminal.server4history.icpcHDU;

import java.util.Scanner;

public class HDU2004 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer score;
        String res;
        while (in.hasNext()) {
            score = in.nextInt();
            if (score < 0 || score > 100) {
                res = "Score is error!";
            } else if (score >= 90) {
                res = "A";
            } else if (score >= 80) {
                res = "B";
            } else if (score >= 70) {
                res = "C";
            } else if (score >= 60) {
                res = "D";
            } else {
                res = "E";
            }
            System.out.println(res);
        }
        in.close();
    }
}

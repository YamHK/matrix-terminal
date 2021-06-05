package top.yamhk.terminal.server4history.icpcHDU;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HDU2090WA {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.0");
        Double sum, a, b;
        String str_temp;
        Integer i;
        String[] s;
        sum = 0.0;
        while (in.hasNext()) {
            str_temp = in.nextLine();
            while (str_temp.indexOf("  ") != -1) {
                str_temp = str_temp.replaceAll("  ", " ");
            }
            s = str_temp.split(" ");
            i = 1;
            a = b = 0.0;
            for (; i < s.length; i++) {
                if (s[i] != " ") {
                    a = Double.parseDouble(s[i]);
                    break;
                }
            }
            for (; i < s.length; i++) {
                if (s[i] != " ") {
                    b = Double.parseDouble(s[i]);
                    break;
                }
            }
            sum += a * b;
        }
        System.out.println(df.format(sum));
        in.close();
    }
}

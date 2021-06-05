package top.yamhk.terminal.server4history.icpcHDU;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HDU1004 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, value, max;
        String temp, maxst;
        Map<String, Integer> mp;
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            in.nextLine();
            mp = new HashMap<String, Integer>();
            max = -1;
            maxst = "";
            for (int i = 0; i < n; i++) {
                temp = in.nextLine();
                value = (null == mp.get(temp)) ? 1 : mp.get(temp) + 1;
                if (value > max) {
                    max = value;
                    maxst = temp;
                }
                mp.put(temp, value);
            }
            System.out.println(maxst);
        }
        in.close();
    }
}

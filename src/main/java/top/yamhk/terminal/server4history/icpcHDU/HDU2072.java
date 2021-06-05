package top.yamhk.terminal.server4history.icpcHDU;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HDU2072 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String temp;
        String[] s;
        Set<String> ms;
        while (in.hasNext()) {
            temp = in.nextLine();
            if (temp.startsWith("#")) {
                break;
            }
            s = temp.split(" ");
            ms = new HashSet<String>();
            for (int i = 0; i < s.length; i++) {
                if (s[i].length() == 0) {
                    continue;
                }
                ms.add(s[i]);
                //System.out.println(s[i]+"#"+s[i].length());
            }
            //System.out.println("------------------------");
            System.out.println(ms.size());
        }
        in.close();
    }
}

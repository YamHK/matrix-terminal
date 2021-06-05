package top.yamhk.terminal.server4history.icpcHDU;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class HDU2094 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n, flag;
        String[] s;
        while (in.hasNext()) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            in.nextLine();
            Map<String, String> mp = new HashMap<String, String>();
            Set<String> mt = new HashSet<String>();
            for (int i = 0; i < n; i++) {
                s = in.nextLine().split(" ");
                mt.add(s[0]);
                mt.add(s[1]);
                mp.put(s[1], s[0]);// 记录被打败的人
            }
            flag = 0;
            Iterator<String> it = mt.iterator();
            while (it.hasNext()) {
                flag += (null == mp.get(it.next())) ? 1 : 0;// 计算不败人数
            }
            System.out.println(flag == 1 ? "Yes" : "No");
        }
        in.close();
    }
}
